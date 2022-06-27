package by.it_academy.jd2.hw.example.messenger.model.converters;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledOperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationEntityConverter implements Converter<ScheduledOperation,ScheduledOperationEntity> {

    @Override
    public ScheduledOperationEntity convert(ScheduledOperation source) {
        Schedule schedule = source.getSchedule();
        Operation operation = source.getOperation();

        return ScheduledOperationEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setUser(source.getUser())
                .setStartTime(schedule.getStart_time())
                .setStopTime(schedule.getStop_time())
                .setInterval(schedule.getInterval())
                .setTime_unit(schedule.getTime_unit().name())
                .setAccount(operation.getAccount())
                .setDescription(operation.getDescription())
                .setCurrency(operation.getCurrency())
                .setCategory(operation.getCategory())
                .setValue(operation.getValue())

                .build();
    }


    @Override
    public <U> Converter<ScheduledOperation, U> andThen(Converter<? super ScheduledOperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
