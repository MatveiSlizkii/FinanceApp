package by.it_academy.jd2.hw.example.messenger.services.api;

import java.io.File;
import java.util.UUID;

public interface IMailService {
    boolean sendMailWithAttachment (File file);
    File getAttachment (UUID uuidAttachment);
}
