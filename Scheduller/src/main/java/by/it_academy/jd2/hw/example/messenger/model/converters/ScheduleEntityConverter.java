package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduleEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEntityConverter implements Converter<Schedule, ScheduleEntity> {

    @Override
    public ScheduleEntity convert(Schedule source) {
        return ScheduleEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setStartTime(source.getStart_time())
                .setStopTime(source.getStop_time())
                .setInterval(source.getInterval())
                .setTimeUnit(source.getTime_unit().name())
                .build();
    }

    @Override
    public <U> Converter<Schedule, U> andThen(Converter<? super ScheduleEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
