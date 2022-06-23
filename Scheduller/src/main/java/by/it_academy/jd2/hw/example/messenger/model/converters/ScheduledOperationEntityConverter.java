package by.it_academy.jd2.hw.example.messenger.model.converters;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledOperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
                .setUser(source.getUser())
                .build();
    }


    @Override
    public <U> Converter<ScheduledOperation, U> andThen(Converter<? super ScheduledOperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
