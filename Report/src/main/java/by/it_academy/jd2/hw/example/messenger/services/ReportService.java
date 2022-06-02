package by.it_academy.jd2.hw.example.messenger.services;

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
import java.io.IOException;
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

    @PersistenceContext
    private EntityManager em;

    public ReportService(IReportStorage reportStorage, ConversionService conversionService,
                         ReportHandlerFactory handlerFactory, ICloudStorage cloud) {
        this.reportStorage = reportStorage;
        this.conversionService = conversionService;
        this.restTemplate = new RestTemplate();
        this.handlerFactory = handlerFactory;
        this.cloud = cloud;
    }

    @Override
    @Transactional
    public Report save(ReportType reportType, Map<String, Object> params) throws IOException {
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
            //TODO генерим ексель файл
            IReportHandler handler = this.handlerFactory.handler(reportType);
            byte[] dataReport = handler.handle(params);
            //TODO сохраняем в облаке и получаем ссылку
            String urlExcel = cloud.upload(dataReport);

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
                .build();

        ReportEntity reportEntity = conversionService.convert(report, ReportEntity.class);
        //TODO доделать сохранение
        reportStorage.save(reportEntity);
        return report;
    }

    @Override
    public Report get(UUID uuid) {
        ReportEntity reportEntity = reportStorage.getById(uuid);
        System.out.println();
        return conversionService.convert(reportEntity, Report.class);
    }

    @Override
    @Transactional
    public Page<Report> getAll(Pageable pageable) {
        List<Report> reports = new ArrayList<>();
        reportStorage.findAll().forEach((o) ->
                reports.add(conversionService.convert(o, Report.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reports.size());
        return new PageImpl<>(reports.subList(start, end), pageable, reports.size());

    }

    @Override
    public Report upgrade(Report reportRaw) {

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
            reportEntity.setParams(reportRaw.getParams().toString());
        }
        if (reportRaw.getExcelReport() != null) {
            reportEntity.setExcelReport(reportRaw.getExcelReport());
        }
        return conversionService.convert(reportEntity, Report.class);
    }
}

