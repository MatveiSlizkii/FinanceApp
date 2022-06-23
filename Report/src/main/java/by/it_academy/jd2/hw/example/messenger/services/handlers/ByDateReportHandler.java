package by.it_academy.jd2.hw.example.messenger.services.handlers;

import by.it_academy.jd2.hw.example.messenger.model.Account;
import by.it_academy.jd2.hw.example.messenger.model.Operation;
import by.it_academy.jd2.hw.example.messenger.services.DataReport;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import by.it_academy.jd2.hw.example.messenger.services.handlers.api.IReportHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ByDateReportHandler implements IReportHandler {
    @Override
    public byte[] handle(Map<String, Object> params){
        List<ValidationError> errors = new ArrayList<>();
        List<String> accountRaw = null;
        Long toLong = null;
        Long fromLong = null;
        try {
            accountRaw = (List<String>) params.get("accounts");
            toLong = Long.parseLong(params.get("to").toString());
            fromLong = Long.parseLong(params.get("from").toString());
        } catch (IllegalArgumentException e){
            errors.add(new ValidationError("params", MessageError.INCORRECT_PARAMS));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        Workbook book1 = new HSSFWorkbook();
        DataReport reportHandler = new DataReport();
        //получение лист аккаунтов
        List<UUID> accountUuids = new ArrayList<>();
        accountRaw.forEach((o) ->
                accountUuids.add(UUID.fromString(o)));
        //сгенерили лист аккаунтов
        List<Account> accountList = new ArrayList<>();
        accountUuids.forEach((o) ->
                accountList.add(reportHandler.getAccount(o)));
        //получаем лист листов операций
        List<List<Operation>> operationsList = new ArrayList<>();
        Long finalToLong = toLong;
        Long finalFromLong = fromLong;
        accountList.forEach((o) ->
                operationsList.add(reportHandler.getOperations(o.getUuid(), finalToLong, finalFromLong)));

        //получили лист операций
        for (int i = 0; i < operationsList.size(); i++) {
            Sheet sheet = book1.createSheet("Баланс " + accountList.get(i).getTitle());
            Row rawTitle = sheet.createRow(0);
            Cell headTitleMain = rawTitle.createCell(0);
            headTitleMain.setCellValue("Отчет по датам" + "" + " на момент ");

            Row row0 = sheet.createRow(2);
            Cell headTitle = row0.createCell(0);
            headTitle.setCellValue("Дата");
            Cell headDescription = row0.createCell(1);
            headDescription.setCellValue("Категория");
            Cell headType = row0.createCell(2);
            headType.setCellValue("Описание");
            Cell headDescription1 = row0.createCell(3);
            headDescription1.setCellValue("Прибыль");
            Cell headTypeCurrency = row0.createCell(4);
            headTypeCurrency.setCellValue("Убыток");
            // теперь надо сделать мапу "дата, операция"
            List<Operation> operationListMain = operationsList.get(i);
            Map<String, List<Operation>> mainMap = new LinkedHashMap<>();
            for (Operation o : operationListMain) {
                String date = o.getDate().format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
                if (!mainMap.containsKey(date)) {
                    List<Operation> tmp = new ArrayList<>();
                    tmp.add(o);
                    mainMap.put(date, tmp);
                } else {
                    List<Operation> tmp1 = mainMap.get(date);
                    tmp1.add(o);
                    mainMap.put(date, tmp1);
                }
            }
            int numRow = 3;
            for (Map.Entry<String, List<Operation>> entry : mainMap.entrySet()) {
                Row row1 = sheet.createRow(numRow);
                Cell title = row1.createCell(0);
                title.setCellValue(entry.getKey());
                Cell date = row1.createCell(1);
                date.setCellValue(String.valueOf(entry.getValue().get(0).getCategory()));
                Cell description = row1.createCell(2);
                description.setCellValue(entry.getValue().get(0).getDescription());
                //относим приход или уход
                if (entry.getValue().get(0).getValue() > 0) {
                    Cell value = row1.createCell(3);
                    value.setCellValue(entry.getValue().get(0).getValue());
                } else {
                    Cell value = row1.createCell(4);
                    value.setCellValue(entry.getValue().get(0).getValue());
                }

                for (int j = 1; j < entry.getValue().size(); j++) {
                    Row row2 = sheet.createRow(numRow + 1);
                    Cell date1 = row2.createCell(1);
                    date1.setCellValue(String.valueOf(entry.getValue().get(j).getCategory()));
                    Cell description1 = row2.createCell(2);
                    description1.setCellValue(entry.getValue().get(j).getDescription());
                    //относим приход или уход
                    if (entry.getValue().get(j).getValue() > 0) {
                        Cell value1 = row2.createCell(3);
                        value1.setCellValue(entry.getValue().get(j).getValue());
                    } else {
                        Cell value1 = row2.createCell(4);
                        value1.setCellValue(entry.getValue().get(j).getValue());
                    }
                    numRow++;
                }
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            book1.write(bos);
            bos.close();
        } catch (IOException e) {
            throw new ValidationException("Не удалось сохранить ексель файл");
        }

        byte[] bytes = bos.toByteArray();
        //claudinary.upload(bytes);
        return bytes;
    }
}
