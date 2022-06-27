package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledOperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationConverter implements Converter<ScheduledOperationEntity, ScheduledOperation> {
    @Autowired
    ConversionService conversionService;
    @Autowired
    private IScheduledOperationService scheduledOperationService;

    @Override
    public ScheduledOperation convert(ScheduledOperationEntity source) {

        return ScheduledOperation.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setOperation(Operation.Builder.createBuilder()
                        .setAccount(source.getAccount())
                        .setCategory(source.getCategory())
                        .setCurrency(source.getCurrency())
                        .setValue(source.getValue())
                        .setDescription(source.getDescription())
                        .setLogin(source.getUser())
                        .build())
                .setSchedule(Schedule.Builder.createBuilder()
                        .setTimeUnit(TimeUnitEnum.valueOf(source.getTime_unit()))
                        .setInterval(source.getInterval())
                        .setStartTime(source.getStartTime())
                        .setStopTime(source.getStopTime())
                        .build())
                .setUser(source.getUser())
                .build();
    }


    @Override
    public <U> Converter<ScheduledOperationEntity, U> andThen(Converter<? super ScheduledOperation, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
