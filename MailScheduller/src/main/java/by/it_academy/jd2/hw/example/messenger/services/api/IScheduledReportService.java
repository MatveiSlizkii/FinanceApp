package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IScheduledReportService {
    ScheduledReport create (ScheduledReport scheduledReport);
    ScheduledReport get (UUID uuid);
    ScheduledReport updateReport (UUID uuid, Report reportRaw);
    Page<ScheduledReport> getAll (Pageable pageable);

}
