package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.entity.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
@Component
public class OperationEntityConverter implements Converter<Operation, OperationEntity> {
    @Override
    public OperationEntity convert(Operation source) {

        return OperationEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setAccount(source.getAccount())
                .setDescription(source.getDescription())
                .setValue(source.getValue())
                .setCurrency(source.getCurrency())
                .setCategory(source.getCategory())
                .build();
    }

    @Override
    public <U> Converter<Operation, U> andThen(Converter<? super OperationEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
