package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledReportEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReportConverter implements Converter<ScheduledReport, ScheduledReportEntity> {
    @Override
    public ScheduledReportEntity convert(ScheduledReport source) {
        Report report = source.getReport();
        Schedule schedule = source.getSchedule();
        return ScheduledReportEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())

                .setStartTime(schedule.getStartTime())
                .setStopTime(schedule.getStopTime())
                .setInterval(schedule.getInterval())
                .setTime(schedule.getTimeUnit())

                .setAccounts(report.getAccounts())
                .setTo(report.getTo())
                .setFrom(report.getFrom())
                .setReportType(report.getReportType().name())
                .setLogin(report.getLogin())
                .build();
    }

    @Override
    public <U> Converter<ScheduledReport, U> andThen(Converter<? super ScheduledReportEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
