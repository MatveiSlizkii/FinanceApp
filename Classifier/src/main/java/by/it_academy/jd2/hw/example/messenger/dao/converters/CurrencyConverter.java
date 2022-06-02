package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CurrencyConverter implements Converter<CurrencyEntity, Currency> {
    @Override
    public Currency convert(CurrencyEntity source) {

        return Currency.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDt_create())
                .setDt_update(source.getDt_update())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .build();
    }

    @Override
    public <U> Converter<CurrencyEntity, U> andThen(Converter<? super Currency, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
