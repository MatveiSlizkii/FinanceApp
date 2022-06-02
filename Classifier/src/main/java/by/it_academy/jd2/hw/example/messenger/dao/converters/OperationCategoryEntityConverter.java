package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class OperationCategoryEntityConverter implements Converter<OperationCategory, OperationCategoryEntity> {
    @Override
    public OperationCategoryEntity convert(OperationCategory source) {

        return OperationCategoryEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setTitle(source.getTitle())
                .build();
    }

    @Override
    public <U> Converter<OperationCategory, U> andThen(Converter<? super OperationCategoryEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
