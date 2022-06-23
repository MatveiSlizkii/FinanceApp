package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import by.it_academy.jd2.hw.example.messenger.services.claudinary.api.ICloudStorage;
import by.it_academy.jd2.hw.example.messenger.dao.api.IReportStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entities.ReportEntity;
import by.it_academy.jd2.hw.example.messenger.model.*;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.api.StatusType;
import by.it_academy.jd2.hw.example.messenger.services.api.IReportService;
import by.it_academy.jd2.hw.example.messenger.services.handlers.ReportHandlerFactory;
import by.it_academy.jd2.hw.example.messenger.services.handlers.api.IReportHandler;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService implements IReportService {
    private final IReportStorage reportStorage;
    private final ConversionService conversionService;
    private final RestTemplate restTemplate;
    private final ICloudStorage cloud;
    private final ReportHandlerFactory handlerFactory;
    private final UserHolder userHolder;

    @PersistenceContext
    private EntityManager em;

    //TODO перебить ентити менеджер
    public ReportService(IReportStorage reportStorage, ConversionService conversionService,
                         ReportHandlerFactory handlerFactory, ICloudStorage cloud, UserHolder userHolder) {
        this.reportStorage = reportStorage;
        this.conversionService = conversionService;
        this.restTemplate = new RestTemplate();
        this.handlerFactory = handlerFactory;
        this.cloud = cloud;
        this.userHolder = userHolder;
    }

    @Override
    @Transactional
    public Report save(ReportType reportType, Map<String, Object> params) {

        //Генерация description
        String description;
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d.MM.uuuu");
        if (reportType.equals(ReportType.BALANCE)) {
            description = "Отчет о балансе на момент " + LocalDateTime.now().toLocalDate().format(formatters);
        } else {
            //преобразование даты To
            long longTo = Long.parseLong(params.get("to").toString());
            LocalDateTime localDateTimeTo = conversionService.convert(longTo, LocalDateTime.class);
            String to = localDateTimeTo.toLocalDate().format(formatters);
            //преобразование даты From
            long longFrom = Long.parseLong(params.get("from").toString());
            LocalDateTime localDateTimeFrom = conversionService.convert(longFrom, LocalDateTime.class);
            String from = localDateTimeFrom.toLocalDate().format(formatters);
            description = "Дата совершения операции: " + to + " - " + from;
        }
        //TODO разбить на подзадачи
        IReportHandler handler = this.handlerFactory.handler(reportType);
        byte[] dataReport = handler.handle(params);
        //TODO вторая стадия
        String urlExcel = cloud.upload(dataReport);

        //TODO разобраться с типами статуса
        LocalDateTime localDateTime = LocalDateTime.now();
        Report report = Report.Builder.createBuilder()
                .setUuid(UUID.randomUUID())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime)
                .setStatus(StatusType.PROGRESS)
                .setType(reportType)
                .setDescription(description)
                .setParams(params.toString())
                .setExcelReport(urlExcel)
                .setUser(userHolder.getLoginFromContext())
                .build();

        ReportEntity reportEntity = conversionService.convert(report, ReportEntity.class);
        reportStorage.save(reportEntity);
        return report;
    }

    @Override
    public Report get(UUID uuid) {
        String login = userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        ReportEntity reportEntity = null;
        try {
            reportEntity = reportStorage.findByUuidAndUser(uuid, login);
        } catch (RuntimeException e){
            errors.add(new ValidationError("report",
                    "данного отчета у пользователя не найденго"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        return conversionService.convert(reportEntity, Report.class);
    }

    @Override
    @Transactional
    public Page<Report> getAll(Pageable pageable) {
        List<ValidationError> errors = new ArrayList<>();

        List<Report> reports = new ArrayList<>();

        reportStorage.findAllByUser(userHolder.getLoginFromContext()).forEach((o) ->
                reports.add(conversionService.convert(o, Report.class)));
        if (reports.isEmpty()){
            errors.add(new ValidationError("report", "У вас пока нет ни одного отчета"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reports.size());
        return new PageImpl<>(reports.subList(start, end), pageable, reports.size());

    }

    @Override
    public Report update(Report reportRaw) {
        //TODO чек репорта
        //TODO есть ли такой ууид
        ReportEntity reportEntity = em.find(ReportEntity.class, reportRaw.getUuid());
        em.refresh(reportEntity, LockModeType.OPTIMISTIC);

        if (reportRaw.getStatus() != null) {
            reportEntity.setStatus(reportRaw.getStatus());
        }
        if (reportRaw.getType() != null) {
            reportEntity.setType(reportRaw.getType());
        }
        if (reportRaw.getDescription() != null) {
            reportEntity.setDescription(reportRaw.getDescription());
        }
        if (reportRaw.getParams() != null) {
            reportEntity.setParams(reportRaw.getParams());
        }
        if (reportRaw.getExcelReport() != null) {
            reportEntity.setExcelReport(reportRaw.getExcelReport());
        }
        return conversionService.convert(reportEntity, Report.class);
    }

    @Override
    public Report updateStatus(UUID uuidReport, StatusType statusType) {
        List<ValidationError> errors = new ArrayList<>();
        ReportEntity reportEntity = null;
        try {
            reportEntity = em.find(ReportEntity.class, uuidReport);
        } catch (RuntimeException e){
            errors.add(new ValidationError("uuidReport", MessageError.ID_NOT_EXIST));
        }
        if (statusType == null){
            errors.add(new ValidationError("statusType", MessageError.MISSING_FIELD));
        }
        em.refresh(reportEntity, LockModeType.OPTIMISTIC);
        reportEntity.setStatus(statusType);

        return conversionService.convert(reportEntity, Report.class);
    }
}

