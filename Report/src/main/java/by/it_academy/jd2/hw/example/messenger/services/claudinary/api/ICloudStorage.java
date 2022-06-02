package by.it_academy.jd2.hw.example.messenger.services.claudinary.api;

import java.io.File;

public interface ICloudStorage {
    String upload (byte[] file);
    byte[] download(String link);
}
