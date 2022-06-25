package by.it_academy.jd2.hw.example.messenger.services.handlers;

import by.it_academy.jd2.hw.example.messenger.model.Account;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BalanceReportHandler implements IReportHandler {


    @Override
    public byte[] handle(Map<String, Object> params) {
        List<ValidationError> errors = new ArrayList<>();
        List<String> accountRaw = null;

        try {
            accountRaw = (List<String>) params.get("accounts");
        } catch (IllegalArgumentException e){
            errors.add(new ValidationError("params", MessageError.INCORRECT_PARAMS));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }



        Workbook book = new HSSFWorkbook();
        DataReport reportHandler = new DataReport();
//получение лист аккаунтов
        List<UUID> accountUuids = new ArrayList<>();
        accountRaw.forEach((o) ->
                accountUuids.add(UUID.fromString(o)));
        //сгенерили лист аккаунтов
        List<Account> accountList = new ArrayList<>();
        accountUuids.forEach((o) ->
                accountList.add(reportHandler.getAccount(o)));
        //получаем мапу курренси
        Map<UUID, String> mapCurrency = reportHandler.getMapCurrency();
        //создаем Excel
        Sheet sheet = book.createSheet("Отчет о балансах");
        //шапка
        Row rawTitle = sheet.createRow(0);
        Cell headTitleMain = rawTitle.createCell(0);
        headTitleMain.setCellValue("Отчет балансов на момент " + LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")));

        Row row0 = sheet.createRow(2);
        Cell headTitle = row0.createCell(0);
        headTitle.setCellValue("Название");

        Cell headDescription = row0.createCell(1);
        headDescription.setCellValue("Описание");
        Cell headType = row0.createCell(2);
        headType.setCellValue("Тип");
        Cell headTypeCurrency = row0.createCell(3);
        headTypeCurrency.setCellValue("Тип валюты");
        Cell headBalance = row0.createCell(4);
        headBalance.setCellValue("Баланс");

        //тело отчета
        for (int i = 0; i < accountList.size(); i++) {
            Row row1 = sheet.createRow(i + 2);
            Cell title = row1.createCell(0);
            title.setCellValue(accountList.get(i).getTitle());
            Cell description = row1.createCell(1);
            description.setCellValue(accountList.get(i).getDescription());
            Cell type = row1.createCell(2);
            type.setCellValue(accountList.get(i).getType());
            Cell typeCurrency = row1.createCell(3);
            String nameCurrency = mapCurrency.get(accountList.get(i).getCurrency());
            typeCurrency.setCellValue(nameCurrency);
            Cell balance = row1.createCell(4);
            balance.setCellValue(accountList.get(i).getBalance());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            book.write(bos);
            bos.close();
        } catch (IOException e) {
            throw new ValidationException("Не удалось сохранить ексель файл");
        }


        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
