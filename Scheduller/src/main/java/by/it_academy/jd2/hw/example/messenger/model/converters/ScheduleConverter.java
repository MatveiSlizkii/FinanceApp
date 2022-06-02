package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.api.TimeUnitEnum;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduleEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConverter implements Converter<ScheduleEntity, Schedule> {
    @Override
    public Schedule convert(ScheduleEntity source) {
        return Schedule.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setStartTime(source.getStartTime())
                .setStopTime(source.getStopTime())
                .setInterval(source.getInterval())
                .setTimeUnit(TimeUnitEnum.valueOf(source.getTime_unit()))
                .build();
    }

    @Override
    public <U> Converter<ScheduleEntity, U> andThen(Converter<? super Schedule, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
