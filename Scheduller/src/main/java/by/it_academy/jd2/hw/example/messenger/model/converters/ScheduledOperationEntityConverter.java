package by.it_academy.jd2.hw.example.messenger.model.converters;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.model.entity.OperationEntity;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduleEntity;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledOperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Component
public class ScheduledOperationEntityConverter implements Converter<ScheduledOperation,ScheduledOperationEntity> {

    @Override
    public ScheduledOperationEntity convert(ScheduledOperation source) {

        return ScheduledOperationEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setOperationEntity(source.getOperation().getUuid())
                .setScheduleEntity(source.getSchedule().getUuid())
                .build();
    }


    @Override
    public <U> Converter<ScheduledOperation, U> andThen(Converter<? super ScheduledOperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
