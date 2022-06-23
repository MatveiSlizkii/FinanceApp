package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.services.api.IMailService;
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
    private final IMailService mailService;

    public RestMailReportController(IMailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping(value = {"/report/{uuid}", "/report/{uuid}/"})
    @ResponseBody
    public String index(@PathVariable(name = "uuid") UUID uuid) {
        File attachment = mailService.getAttachment(uuid);
        mailService.sendMailWithAttachment(attachment);
        return "Отправлено успешно";
    }


}
