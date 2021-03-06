package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.utils.JwtTokenUtil;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {
    private final IAccountStorage accountStorage;
    private final ConversionService conversionService;
    private final IBalanceStorage balanceStorage;
    private final RestTemplate restTemplate;
    private final EntityManager em;
    private final UserHolder userHolder;



    public AccountService(IAccountStorage accountStorage, ConversionService conversionService,
                          IBalanceStorage balanceStorage, EntityManager em, UserHolder userHolder) {
        this.accountStorage = accountStorage;
        this.conversionService = conversionService;
        this.balanceStorage = balanceStorage;
        this.em = em;
        this.restTemplate = new RestTemplate();
        this.userHolder = userHolder;
    }



    @Value("${classifier_currency_url}")
    private String currencyUrl;


    @Override
    @Transactional
    public Account get(UUID uuid) {
        String login = this.userHolder.getLoginFromContext();

        this.checkIdAccount(uuid);

        AccountEntity entity;

        try {
            entity = this.accountStorage.findByUserAndUuid(login, uuid).get();
        } catch (Exception e) {
            throw new RuntimeException(MessageError.SQL_ERROR, e);
        }

        return this.conversionService.convert(entity, Account.class);
    }

    //TODO ?????????????????????????? ?????????????????? ????????????????????
    @Override
    @Transactional
    public Account save(Account account) {
        String login = userHolder.getLoginFromContext();

        List<ValidationError> errors = new ArrayList<>();

        this.checkAccount(account, errors);
        try {
            if (this.accountStorage.findByUserAndTitle(login, account.getTitle()).isPresent()) {
                errors.add(new ValidationError("title (???????????????? ??????????)", MessageError.NO_UNIQUE_FIELD));
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageError.SQL_ERROR, e);
        }


        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }


        LocalDateTime timeNow = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        account.setUuid(uuid);
        account.setDt_create(timeNow);
        account.setDt_update(timeNow);
        account.setBalance((double) 0);
        account.setUser(login);
        BalanceEntity balanceEntity = BalanceEntity.Builder.createBuilder()
                .setDtUpdate(timeNow)
                .setUuid(uuid)
                .setValue(0)
                .build();
        balanceStorage.save(balanceEntity);
        AccountEntity accountEntity = accountStorage.save(conversionService.convert(account, AccountEntity.class));

        return conversionService.convert(accountEntity, Account.class);
    }

    @Override
    @Transactional
    public Page<Account> getAll(Pageable pageable) {
        String login = this.userHolder.getLoginFromContext();


        List<Account> accounts = new ArrayList<>();
        accountStorage.findAllByUser(login).forEach((o) -> accounts.add(
                conversionService.convert(o, Account.class)));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), accounts.size());
        return new PageImpl<>(accounts.subList(start, end), pageable, accounts.size());
    }

    @Override
    @Transactional
    public Account update(UUID uuid, Account accountRaw, Long dt_update) {

        String login = this.userHolder.getLoginFromContext();

        List<ValidationError> errors = new ArrayList<>();
        try {
            if (!this.accountStorage.existsAccountEntityByUserAndUuid(login, uuid)) {
                errors.add(new ValidationError("uuid ??????????", MessageError.ID_NOT_EXIST));
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageError.SQL_ERROR, e);
        }


        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);
        if (!accountStorage.getById(uuid).getDtUpdate().equals(checkDateTime)) {
            errors.add(new ValidationError("dt_update", MessageError.MISSING_OBJECT));
        }

        this.checkAccount(accountRaw, errors);


        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }

        AccountEntity accountEntity = em.find(AccountEntity.class, uuid);
        em.refresh(accountEntity, LockModeType.OPTIMISTIC);
        accountEntity.setTitle(accountRaw.getTitle());
        accountEntity.setDescription(accountRaw.getDescription());
        accountEntity.setCurrency(accountRaw.getCurrency());
        accountEntity.setUser(accountRaw.getUser());
        accountEntity.setUser(userHolder.getLoginFromContext());
        this.updateBalanceMain(uuid, accountRaw.getBalance());



        return conversionService.convert(accountEntity, Account.class);
    }

    @Override
    public BalanceEntity updateBalance(UUID uuid, Double value) {
        BalanceEntity balanceEntity = em.find(BalanceEntity.class, uuid);
        em.refresh(balanceEntity, LockModeType.OPTIMISTIC);
        Double valueFinal = balanceEntity.getValue() + value;
        balanceEntity.setValue(valueFinal);


        return balanceEntity;
    }
    public BalanceEntity updateBalanceMain(UUID uuid, Double value) {
        BalanceEntity balanceEntity = em.find(BalanceEntity.class, uuid);
        em.refresh(balanceEntity, LockModeType.OPTIMISTIC);
        balanceEntity.setValue(value);


        return balanceEntity;
    }

    @Override
    public List<UUID> uuidsAccountsByUser(String login) {
        List<AccountEntity> accountEntities = accountStorage.findAllByUser(login);
        List<UUID> uuidList = new ArrayList<>();
        accountEntities.forEach((o)-> uuidList.add(o.getUuid()));
        return uuidList;
    }

    @Override
    public boolean checkAccountByUser(UUID uuidAccount, String login) {
        return accountStorage.existsAccountEntityByUserAndUuid(login, uuidAccount);
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
            String token = JwtTokenUtil.generateAccessToken(this.userHolder.getUser());
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            String currencyClassifierUrl = currencyUrl + account.getCurrency() + "/";
            try {
                this.restTemplate.exchange(currencyClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("currency", MessageError.ID_NOT_EXIST));
            }
        }

        if (account.getTitle() == null || account.getTitle().isEmpty()) {
            errors.add(new ValidationError("title", MessageError.MISSING_FIELD));
        } else {
            Optional<AccountEntity> accountEntities= accountStorage.findByUserAndTitle(userHolder.getLoginFromContext(), account.getTitle());
            if (!accountEntities.isEmpty()){
                errors.add(new ValidationError("title", MessageError.MISSING_FIELD));
            }
        }
    }

    private void checkIdAccount(UUID idAccount, List<ValidationError> errors) {
        String login = this.userHolder.getLoginFromContext();

        if (idAccount == null) {
            errors.add(new ValidationError("uuid ??????????", MessageError.MISSING_FIELD));
            return;
        }

        try {
            if (!this.accountStorage.existsAccountEntityByUserAndUuid(login, idAccount)) {
                errors.add(new ValidationError("uuid ??????????", MessageError.ID_NOT_EXIST));
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageError.SQL_ERROR, e);
        }
    }

    private void checkIdAccount(UUID idAccount) {
        List<ValidationError> errors = new ArrayList<>();

        this.checkIdAccount(idAccount, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
    }

}
