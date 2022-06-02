package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class OperationCategoryConverter implements Converter<OperationCategoryEntity, OperationCategory> {
    @Override
    public OperationCategory convert(OperationCategoryEntity source) {

        return OperationCategory.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setTitle(source.getTitle())
                .build();
    }

    @Override
    public <U> Converter<OperationCategoryEntity, U> andThen(Converter<? super OperationCategory, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
