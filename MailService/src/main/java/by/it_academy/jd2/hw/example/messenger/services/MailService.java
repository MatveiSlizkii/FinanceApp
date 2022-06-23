package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.services.api.IMailService;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;

@Service
public class MailService implements IMailService {
    private final RestTemplate restTemplate;
    private final JavaMailSender emailSender;
    private final UserHolder userHolder;

    public MailService(JavaMailSender emailSender, UserHolder userHolder) {
        this.restTemplate = new RestTemplate();
        this.emailSender = emailSender;
        this.userHolder = userHolder;
    }

    @Transactional
    @Override
    public boolean sendMailWithAttachment(File file){
        //TODO перебить ссылки
        //TODO перебить с хидером
        ResponseEntity<Collection> response =
                restTemplate.getForEntity(
                        "currencyUrl",
                        Collection.class);
        Collection collectionReportData = response.getBody();
        Map<String, String> mapReportData = (Map<String, String>) collectionReportData;
        String description = mapReportData.get("description");

        String login = userHolder.getLoginFromContext();
        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("my.finance.app@mail.ru");
            helper.setTo(login);
            helper.setSubject("Отчет");
            helper.setText(description);
            FileSystemResource file1
                    = new FileSystemResource(file);
            helper.addAttachment("Invoice.xls", file1);

            emailSender.send(message);
        } catch (MessagingException e){
            throw new ValidationException("Произошла ошибка при отправке отчета по почте");
        }
        return true;
    }

    @Override
    public File getAttachment(UUID uuidAttachment) {

        ResponseEntity<byte[]> response1 =
                restTemplate.getForEntity(
                        "http://localhost:8083/api/account/" + uuidAttachment + "/export",
                        byte[].class);
        byte[] raw = response1.getBody();

        File file = new File("test.xls");
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(raw);
        } catch (IOException e) {
            throw new ValidationException("Файл с данного отчета не найден");
        }
        return file;
    }
}
