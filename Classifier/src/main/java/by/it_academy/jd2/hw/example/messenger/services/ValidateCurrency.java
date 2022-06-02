package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.errors.ArgumentError;
import by.it_academy.jd2.hw.example.messenger.errors.ArgumentException;
import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IValidate;

public class ValidateCurrency implements IValidate<CurrencyEntity> {
    @Override
    public void validate(CurrencyEntity currencyEntity) {
        if (currencyEntity.getTitle() == null){
            ArgumentError argumentError = new ArgumentError("Title",
                    "Не передано название валюты");

            throw new ArgumentException("Ошибка создания типа валюты", argumentError);
        }
    }
}
