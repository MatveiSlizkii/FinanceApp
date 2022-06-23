package by.it_academy.jd2.hw.example.messenger.model.converters;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.OperationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationRequestConverter implements Converter<Operation, OperationRequest> {
    @Override
    public OperationRequest convert(Operation source) {

        return OperationRequest.Builder.createBuilder()
                .setDescription(source.getDescription())
                .setValue(source.getValue())
                .setCurrency(source.getCurrency())
                .setCategory(source.getCategory())
                .build();
    }

    @Override
    public <U> Converter<Operation, U> andThen(Converter<? super OperationRequest, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
