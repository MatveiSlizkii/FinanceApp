package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.ICurrencyStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import by.it_academy.jd2.hw.example.messenger.services.api.ICurrencyService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CurrencyService implements ICurrencyService {
    private final ICurrencyStorage currencyStorage;
    private final ConversionService conversionService;

    public CurrencyService(ICurrencyStorage currencyStorage,
                           ConversionService conversionService) {
        this.currencyStorage = currencyStorage;
        this.conversionService = conversionService;
    }

    @Override
    public Currency get(UUID uuid) {
        return conversionService.convert(currencyStorage.getById(uuid), Currency.class);
    }

    @Override
    public Currency save(Currency currency) {
//        IValidate<CurrencyEntity> validate = new ValidateCurrency();
//        validate.validate(currencyEntity);
        LocalDateTime localDateTime = LocalDateTime.now();
        currency.setUuid(UUID.randomUUID());
        currency.setDt_create(localDateTime);
        currency.setDt_update(localDateTime);
        CurrencyEntity currencyEntity = conversionService.convert(currency, CurrencyEntity.class);
        currencyStorage.save(currencyEntity);
        //TODO сделать на уникальные title
        return currency;
    }

    @Override
    public Page<Currency> getAll(Pageable pageable) {
        List<Currency> currencies = new ArrayList<>();
        currencyStorage.findAll().forEach((o)->
                currencies.add(conversionService.convert(o, Currency.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), currencies.size());
        return new PageImpl<>(currencies.subList(start, end), pageable, currencies.size());
    }

    @Override
    public List<Currency> getAllList() {
        List<Currency> currencies = new ArrayList<>();
        currencyStorage.findAll().forEach((o)->
                currencies.add(conversionService.convert(o, Currency.class)));
        return currencies;
    }
}
