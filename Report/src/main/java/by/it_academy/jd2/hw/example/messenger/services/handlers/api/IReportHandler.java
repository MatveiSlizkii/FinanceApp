package by.it_academy.jd2.hw.example.messenger.services.handlers.api;


import java.io.IOException;
import java.util.Map;

public interface IReportHandler {
    byte[] handle(Map<String, Object> params) throws IOException;
}
