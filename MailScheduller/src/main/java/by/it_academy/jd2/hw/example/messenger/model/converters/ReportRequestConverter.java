package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.ReportRequest;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class ReportRequestConverter implements Converter<Report, ReportRequest> {


    @Override
    public ReportRequest convert(Report source) {
        return ReportRequest.Builder.createBuilder()
                .setAccounts(source.getAccounts())
                .setTo(source.getTo().toInstant(TimeZone.getDefault().toZoneId().
                        getRules().getOffset(source.getTo())).toEpochMilli())
                .setFrom(source.getFrom().toInstant(TimeZone.getDefault().toZoneId().
                        getRules().getOffset(source.getFrom())).toEpochMilli())
                .build();
    }

    @Override
    public <U> Converter<Report, U> andThen(Converter<? super ReportRequest, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
