package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import by.it_academy.jd2.hw.example.messenger.dao.api.IAccountStorage;
import by.it_academy.jd2.hw.example.messenger.dao.api.IBalanceStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.BalanceEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.dao.entity.AccountEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {
    private final IAccountStorage accountStorage;
    private final ConversionService conversionService;
    private final IBalanceStorage balanceStorage;
    private final RestTemplate restTemplate;
    private final EntityManager em;

    public AccountService(IAccountStorage accountStorage, ConversionService conversionService,
                          IBalanceStorage balanceStorage, EntityManager em) {
        this.accountStorage = accountStorage;
        this.conversionService = conversionService;
        this.balanceStorage = balanceStorage;
        this.em = em;
        this.restTemplate = new RestTemplate();
    }

    @Value("${classifier_currency_url}")
    private String currencyUrl;


    @Override
    @Transactional
    public Account get(UUID uuid) {

        return conversionService.convert(accountStorage.getById(uuid), Account.class);
    }

    //TODO перепроверить аннотацию транзакция
    @Override
    //@Transactional
    public Account save(Account account) {
        List<ValidationError> errors = new ArrayList<>();

        this.checkAccount(account, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }


        LocalDateTime timeNow = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        account.setUuid(uuid);
        account.setDt_create(timeNow);
        account.setDt_update(timeNow);
        account.setBalance((double) 0);
        BalanceEntity balanceEntity = BalanceEntity.Builder.createBuilder()
                .setDtUpdate(timeNow)
                .setUuid(uuid)
                .setValue(0)
                .build();
        balanceStorage.save(balanceEntity);
        accountStorage.save(conversionService.convert(account, AccountEntity.class));

        return account;
    }

    @Override
    @Transactional
    public Page<Account> getAll(Pageable pageable) {

        List<Account> accounts = new ArrayList<>();
        accountStorage.findAll().forEach((o) -> accounts.add(
                conversionService.convert(o, Account.class)));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), accounts.size());
        return new PageImpl<>(accounts.subList(start, end), pageable, accounts.size());
    }

    @Override
    @Transactional
    public Account update(UUID uuid, Account accountRaw, Long dt_update) {

        List<ValidationError> errors = new ArrayList<>();

        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);
        if (!accountStorage.getById(uuid).getDtUpdate().equals(checkDateTime)) {
            errors.add(new ValidationError("dt_update", MessageError.MISSING_OBJECT));
        }

        this.checkAccount(accountRaw, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }

        AccountEntity accountEntity = em.find(AccountEntity.class, uuid);
        em.refresh(accountEntity, LockModeType.OPTIMISTIC);
        //TODO ошибка если не удалось обновить систему

        return conversionService.convert(accountStorage.getById(uuid), Account.class);
    }

    @Override
    public BalanceEntity updateBalance(UUID uuid, Double value) {
        BalanceEntity balanceEntity = em.find(BalanceEntity.class, uuid);
        em.refresh(balanceEntity, LockModeType.OPTIMISTIC);
        Double valueFinal = balanceEntity.getValue() + value;
        balanceEntity.setValue(valueFinal);

        //TODO ошибка если не обновилось

        return balanceEntity;
    }


    private void checkAccount(Account account, List<ValidationError> errors) {

        if (account == null) {
            errors.add(new ValidationError("account", MessageError.MISSING_OBJECT));
            return;
        }

        if (account.getType() == null) {
            errors.add(new ValidationError("type", MessageError.MISSING_FIELD));
        }

        if (account.getCurrency() == null) {
            errors.add(new ValidationError("currency", MessageError.MISSING_FIELD));
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            try {
                String currencyClassifierUrl = currencyUrl + account.getCurrency() + "/";
                this.restTemplate.exchange(currencyClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("currency", MessageError.ID_NOT_EXIST));
            }
        }

        if (account.getTitle() == null || account.getTitle().isEmpty()) {
            errors.add(new ValidationError("title", MessageError.MISSING_FIELD));
        } else {
            //TODO когда будет юзер чтобы проверяло название счета и юзера
        }
    }

}
