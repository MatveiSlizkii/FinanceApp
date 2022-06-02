package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class OperationConverter implements Converter<OperationEntity, Operation> {
    @Override
    public Operation convert(OperationEntity source) {

        return Operation.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setDate(source.getDate())
                .setDescription(source.getDescription())
                .setCategory(source.getCategory())
                .setValue(source.getValue())
                .setCurrency(source.getCurrency())
                .setUuidAccount(source.getUuidAccount())
                .build();
    }

    @Override
    public <U> Converter<OperationEntity, U> andThen(Converter<? super Operation, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
