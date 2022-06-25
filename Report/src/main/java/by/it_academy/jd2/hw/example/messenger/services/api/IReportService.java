package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.Report;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.api.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface IReportService {

    Report save (ReportType reportType, Map<String, Object> params);
    byte[] CreateExcel (ReportType reportType,Map<String,Object> params, UUID uuidReport);
    String uploadInCloud (byte[] bytes,UUID uuidReport);
    Page<Report> getAll (Pageable pageable);
    Report get (UUID uuid);
    Report update(Report reportRaw);


}
