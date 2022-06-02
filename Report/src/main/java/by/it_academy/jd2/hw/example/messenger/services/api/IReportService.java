package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.Report;
import by.it_academy.jd2.hw.example.messenger.model.ReportParamBalance;
import by.it_academy.jd2.hw.example.messenger.model.ReportParamByCategory;
import by.it_academy.jd2.hw.example.messenger.model.ReportParamByDate;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IReportService {

    Report save (ReportType reportType, Map<String, Object> params) throws IOException;
    Page<Report> getAll (Pageable pageable);
    Report get (UUID uuid);
    Report upgrade(Report reportRaw);

}
