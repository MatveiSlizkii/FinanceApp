package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.ScheduledReportStorage;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledReportEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledReportService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import by.it_academy.jd2.hw.example.messenger.services.scheduler.SchedulerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduledReportService implements IScheduledReportService {
    private final ConversionService conversionService;
    private final ScheduledReportStorage scheduledReportStorage;
    private final SchedulerService schedulerService;

    @PersistenceContext
    private EntityManager em;

    public ScheduledReportService(ConversionService conversionService,
                                  ScheduledReportStorage scheduledReportStorage,
                                  SchedulerService schedulerService) {
        this.conversionService = conversionService;
        this.scheduledReportStorage = scheduledReportStorage;
        this.schedulerService = schedulerService;
    }

    @Override
    @Transactional
    public ScheduledReport create(ScheduledReport scheduledReport) {
        //TODO ошибки
        //TODO ошибка на то твои ли аккаунты
        LocalDateTime localDateTime = LocalDateTime.now();
        scheduledReport.setUuid(UUID.randomUUID());
        scheduledReport.setDtCreate(localDateTime);
        scheduledReport.setDtUpdate(localDateTime);

        ScheduledReportEntity scheduledReportEntity = conversionService.convert(scheduledReport, ScheduledReportEntity.class);
        scheduledReportStorage.save(scheduledReportEntity);

        schedulerService.create(scheduledReport);

        return scheduledReport;
    }

    @Override
    public ScheduledReport get(UUID uuid) {
        //TODO ошибка не найдено
        //TODO проверить твои ли аккаунты
        ScheduledReportEntity scheduledReportEntity = scheduledReportStorage.getById(uuid);
        return conversionService.convert(scheduledReportEntity, ScheduledReport.class);
    }

    @Override
    public ScheduledReport updateReport(UUID uuid, Report reportRaw) {
        //TODO проверить ууид
        //TODO проверить репорт
        //TODO перебить как в шедулере
        ScheduledReportEntity sre = em.find(ScheduledReportEntity.class, uuid);
        em.refresh(sre, LockModeType.OPTIMISTIC);

        if (!sre.getAccounts().equals(reportRaw.getAccounts()) && reportRaw.getAccounts() != null){
            sre.setAccounts(reportRaw.getAccounts());
        }
        if (!sre.getTo().equals(reportRaw.getTo()) && reportRaw.getTo() != null){
            sre.setTo(reportRaw.getTo());
        }
        if (!sre.getFrom().equals(reportRaw.getFrom()) && reportRaw.getFrom() != null ){
            sre.setFrom(reportRaw.getFrom());
        }
        if (!sre.getReportType().equals(reportRaw.getReportType()) && reportRaw.getReportType() != null){
            sre.setReportType(reportRaw.getReportType().name());
        }

        return conversionService.convert(sre, ScheduledReport.class);
    }

    @Override
    public Page<ScheduledReport> getAll(Pageable pageable) {
        //TODO согласно юзеру
        List<ScheduledReport> scheduledReports = new ArrayList<>();
        scheduledReportStorage.findAll().forEach((o) -> scheduledReports.add(
                conversionService.convert(o, ScheduledReport.class)));

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), scheduledReports.size());
        return new PageImpl<>(scheduledReports.subList(start, end), pageable, scheduledReports.size());
    }

    boolean checkReport (Report report){
        List<ValidationError> errors = new ArrayList<>();
        //TODO перебить на лист
        //TODO сделать проверку на юзера
        if (report.getAccounts().length == 0){
            errors.add(new ValidationError("accounts", MessageError.MISSING_FIELD));
        }
        if (report.getTo() == null){
            errors.add(new ValidationError("to", MessageError.MISSING_FIELD));
        }
        if (report.getFrom() == null){
            errors.add(new ValidationError("from", MessageError.MISSING_FIELD));
        }
        if (report.getReportType() == null){
            errors.add(new ValidationError("reportType", MessageError.MISSING_FIELD));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }

        return true;
    }
}
