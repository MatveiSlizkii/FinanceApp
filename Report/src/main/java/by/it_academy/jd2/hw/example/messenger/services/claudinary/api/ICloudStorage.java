package by.it_academy.jd2.hw.example.messenger.services.claudinary.api;


public interface ICloudStorage {
    String upload (byte[] file);
    byte[] download(String link);
}
