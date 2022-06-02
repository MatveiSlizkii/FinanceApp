package by.it_academy.jd2.hw.example.messenger.services.api;


import by.it_academy.jd2.hw.example.messenger.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICurrencyService {
    Currency get(UUID id);
    Currency save(Currency currency);
    Page<Currency> getAll(Pageable pageable);
    List<Currency> getAllList();
}
