package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.entity.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class OperationConverter implements Converter<OperationEntity, Operation> {
    @Override
    public Operation convert(OperationEntity source) {
        return Operation.Builder.createBuilder()
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
    public <U> Converter<OperationEntity, U> andThen(Converter<? super Operation, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
