package by.it_academy.jd2.hw.example.messenger.dao.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime ldt) {

        return ldt.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(ldt)).toEpochMilli();
    }
}
