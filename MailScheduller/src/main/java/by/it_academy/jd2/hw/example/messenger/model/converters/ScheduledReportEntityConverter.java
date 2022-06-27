package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReportEntityConverter implements Converter<ScheduledReportEntity, ScheduledReport> {
    @Override
    public ScheduledReport convert(ScheduledReportEntity source) {
        return ScheduledReport.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setSchedule(Schedule.Builder.createBuilder()
                        .setStartTime(source.getStartTime())
                        .setStopTime(source.getStopTime())
                        .setInterval(source.getInterval())
                        .setTime(source.getTime())
                        .build())
                .setReport(Report.Builder.createBuilder()
                        .setAccounts(source.getAccounts())
                        .setTo(source.getTo())
                        .setFrom(source.getFrom())
                        .setReportType(ReportType.valueOf(source.getReportType()))
                        .setLogin(source.getLogin())
                        .build())
                .build();
    }

    @Override
    public <U> Converter<ScheduledReportEntity, U> andThen(Converter<? super ScheduledReport, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
