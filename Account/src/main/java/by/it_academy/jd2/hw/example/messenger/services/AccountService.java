package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IAccountStorage;
import by.it_academy.jd2.hw.example.messenger.dao.api.IBalanceStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.BalanceEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.dao.entity.AccountEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import by.it_academy.jd2.hw.example.messenger.services.api.IValidateArgument;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @PersistenceContext
    private EntityManager em;


    public AccountService(IAccountStorage accountStorage, ConversionService conversionService, IBalanceStorage balanceStorage) {
        this.accountStorage = accountStorage;
        this.conversionService = conversionService;
        this.balanceStorage = balanceStorage;
    }

    @Override
    @Transactional
    public Account get(UUID uuid) {

        return conversionService.convert(accountStorage.getById(uuid), Account.class);
    }

    @Override
    //@Transactional
    public Account save(Account account) {
        LocalDateTime timeNow = LocalDateTime.now();
//        IValidateArgument validateArgument = new ValidateAccount();
//        validateArgument.validate(account);
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

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), accounts.size());
        return new PageImpl<>(accounts.subList(start, end), pageable, accounts.size());
    }

    @Override
    @Transactional
    public Account update(UUID uuid, Account accountRaw, Long dt_update) {
        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);
        if (!accountStorage.getById(uuid).getDtUpdate().equals(checkDateTime)) {
            throw new IllegalArgumentException("Данная версия для обновления устарела," +
                    "обновите, пожалуйста страницу");
        }
        //selectel
        //hertz
        AccountEntity accountEntity = em.find(AccountEntity.class, uuid);
        em.refresh(accountEntity, LockModeType.OPTIMISTIC);
        if (accountRaw.getTitle() != null) {
            accountEntity.setTitle(accountRaw.getTitle());
        }
        if (accountRaw.getDescription() != null) {
            accountEntity.setDescription(accountRaw.getDescription());
        }
        if (accountRaw.getType() != null) {
            accountEntity.setType(accountRaw.getType().name());
        }
        if (accountRaw.getCurrency() != null) {
            accountEntity.setCurrency(accountRaw.getCurrency());
        }
        if (accountRaw.getBalance() != null){
            updateBalance(accountEntity.getUuid(), accountRaw.getBalance());
        }
        //em.close(); // под вопросом надо ли она, скорее всего нет
        return conversionService.convert(accountStorage.getById(uuid), Account.class);
    }

    @Override
    public BalanceEntity updateBalance(UUID uuid, Double value) {
        BalanceEntity balanceEntity = em.find(BalanceEntity.class, uuid);
        em.refresh(balanceEntity, LockModeType.OPTIMISTIC);
        Double valueFinal = balanceEntity.getValue() + value;
        balanceEntity.setValue(valueFinal);
        return balanceEntity;
    }
}
