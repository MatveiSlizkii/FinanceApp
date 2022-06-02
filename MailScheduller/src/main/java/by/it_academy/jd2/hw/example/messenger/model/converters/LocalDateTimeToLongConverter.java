package by.it_academy.jd2.hw.example.messenger.model.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime localDateTime) {
        return localDateTime.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(localDateTime)).toEpochMilli();
    }
}
