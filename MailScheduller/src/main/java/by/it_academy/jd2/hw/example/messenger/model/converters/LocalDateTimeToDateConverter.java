package by.it_academy.jd2.hw.example.messenger.model.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
    @Override
    public Date convert(LocalDateTime ldt) {
        return Date
                .from(ldt.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
