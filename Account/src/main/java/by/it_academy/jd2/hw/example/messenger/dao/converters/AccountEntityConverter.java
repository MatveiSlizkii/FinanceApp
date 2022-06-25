package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.dao.api.IBalanceStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.BalanceEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.dao.entity.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityConverter implements Converter<Account, AccountEntity> {

    private  final IBalanceStorage balanceStorage;

    public AccountEntityConverter(IBalanceStorage balanceStorage) {
        this.balanceStorage = balanceStorage;
    }

    @Override
    public AccountEntity convert(Account source) {
        BalanceEntity balance = balanceStorage.getById(source.getUuid());
        return AccountEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDt_create())
                .setDtUpdate(source.getDt_update())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .setType(source.getType().name())
                .setCurrency(source.getCurrency())
                .setBalance(balance)
                .setUser(source.getUser())
                .build();
    }

    @Override
    public <U> Converter<Account, U> andThen(Converter<? super AccountEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
