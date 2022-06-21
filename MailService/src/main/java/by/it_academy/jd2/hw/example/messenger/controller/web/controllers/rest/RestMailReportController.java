package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/mail")
public class RestMailReportController {
    private RestTemplate restTemplate;
    private final JavaMailSender emailSender;

    public RestMailReportController(JavaMailSender emailSender) {
        this.restTemplate = new RestTemplate();
        this.emailSender = emailSender;
    }

    @RequestMapping(
            value = {"/report/{uuid}", "/report/{uuid}/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public String create(@PathVariable(name = "uuid") UUID uuid) throws IOException, MessagingException {
        //TODO перебить на нормальную ссылку
        ResponseEntity<byte[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8083/api/account/" + uuid + "/export",
                        byte[].class);
        byte[] raw = response.getBody();

        File file = new File("test.xls");
        OutputStream os = new FileOutputStream(file);
        os.write(raw);

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //TODO перебить на имейл
        //TODO перебить на норм текст
        helper.setFrom("my.finance.app@mail.ru");
        helper.setTo("matvei.biz@mail.ru");
        helper.setSubject("subject");
        helper.setText("text");
        //TODO подумать над названием
        FileSystemResource file1
                = new FileSystemResource(file);
        helper.addAttachment("Invoice.xls", file1);

        emailSender.send(message);

        //TODO проверка на сущетсвует ли такой тип
        //TODO проверка на необходимые значение в боди
        return "Good";
    }
//    @RequestMapping(
//            value = {"/report/{mail}", "/report/{mail}/"},
//            method = RequestMethod.GET
//    )
//    @ResponseBody
//    public String test(@PathVariable (name = "mail") String mail) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("my.finance.app@mail.ru");
//        message.setTo(mail);
//        message.setSubject("subject");
//        message.setText("This is the test email template for your email:\n%s\n");
//        emailSender.send(message);
//        return "Отправлено";
//    }

}
