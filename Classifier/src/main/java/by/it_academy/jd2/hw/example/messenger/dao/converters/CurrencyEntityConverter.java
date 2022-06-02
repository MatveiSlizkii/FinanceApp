package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CurrencyEntityConverter implements Converter<Currency, CurrencyEntity> {
    @Override
    public CurrencyEntity convert(Currency source) {

        return CurrencyEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .build();
    }

    @Override
    public <U> Converter<Currency, U> andThen(Converter<? super CurrencyEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
