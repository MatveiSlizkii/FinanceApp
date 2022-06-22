package by.it_academy.jd2.hw.example.messenger.services.handlers;

import by.it_academy.jd2.hw.example.messenger.model.Account;
import by.it_academy.jd2.hw.example.messenger.model.Operation;
import by.it_academy.jd2.hw.example.messenger.services.DataReport;
import by.it_academy.jd2.hw.example.messenger.services.handlers.api.IReportHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class ByCategoryReportHandler implements IReportHandler {
    @Override
    public byte[] handle(Map<String, Object> params) throws IOException {
        DataReport reportHandler = new DataReport();
        Workbook book = new HSSFWorkbook();
        //получение лист аккаунтов
        List<String> accountRaw = (List<String>) params.get("accounts");
        List<UUID> accountUuids = new ArrayList<>();
        accountRaw.forEach((o) ->
                accountUuids.add(UUID.fromString(o)));
        //сгенерили лист аккаунтов
        List<Account> accountList = new ArrayList<>();
        accountUuids.forEach((o) ->
                accountList.add(reportHandler.getAccount(o)));
        //получаем мапу курренси
        Map<UUID, String> mapCurrency = reportHandler.getMapCurrency();
        //получаем лист листов операций
        List<List<Operation>> operationsList = new ArrayList<>();
        accountList.forEach((o) ->
                operationsList.add(reportHandler.getOperations(o.getUuid(), Long.parseLong(params.get("to").toString()), Long.parseLong(params.get("from").toString()))));
        //генерируем таблицу Excel

        //привет цикл
        for (int i = 0; i < operationsList.size(); i++) {
            Sheet sheet = book.createSheet("Баланс " + accountList.get(i).getTitle());
            Row rawTitle = sheet.createRow(0);
            Cell headTitleMain = rawTitle.createCell(0);


            headTitleMain.setCellValue("Отчет по категориям");

            Row row0 = sheet.createRow(2);
            Cell headTitle = row0.createCell(0);
            headTitle.setCellValue("Категория");
            Cell headDescription = row0.createCell(1);
            headDescription.setCellValue("дата");
            Cell headType = row0.createCell(2);
            headType.setCellValue("Описание");
            Cell headDescription1 = row0.createCell(3);
            headDescription1.setCellValue("Прибыль");
            Cell headTypeCurrency = row0.createCell(4);
            headTypeCurrency.setCellValue("Убыток");

            List<Operation> operationListMain = operationsList.get(i);
            Map<String, List<Operation>> operationMainMap = new HashMap<>();
            for (Operation o : operationListMain) {
                if (!operationMainMap.containsKey(o.getCategory())) {
                    List<Operation> tmp = new ArrayList<>();
                    tmp.add(o);
                    operationMainMap.put(o.getCategory(), tmp);
                } else {
                    List<Operation> tmp1 = operationMainMap.get(o.getCategory());
                    tmp1.add(o);
                    operationMainMap.put(o.getCategory(), tmp1);
                }
            }

            //мы получили все!!!!
            int numRow = 3;
            for (Map.Entry<String, List<Operation>> entry : operationMainMap.entrySet()) {
                Row row1 = sheet.createRow(numRow);
                Cell title = row1.createCell(0);
                title.setCellValue(entry.getKey());
                Cell date = row1.createCell(1);
                date.setCellValue(String.valueOf(entry.getValue().get(0).getDate()));
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
                    date1.setCellValue(String.valueOf(entry.getValue().get(j).getDate()));
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
//TODO сделать ошибку
        book.write(bos);

        bos.close();

        byte[] bytes = bos.toByteArray();
        // claudinary.upload(bytes);

        return bytes;
    }
}
