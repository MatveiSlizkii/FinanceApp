package by.it_academy.jd2.hw.example.messenger.dao.converters;

import by.it_academy.jd2.hw.example.messenger.model.api.TypeAccount;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.dao.entity.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class AccountConverter implements Converter<AccountEntity, Account> {
    @Override
    public Account convert(AccountEntity source) {

        return Account.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDt_create(source.getDtCreate())
                .setDt_update(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .setType(TypeAccount.valueOf(source.getType()))
                .setCurrency(source.getCurrency())
                .setBalance(source.getBalance().getValue())
                .setUser(source.getUser())
                .build();
    }

    @Override
    public <U> Converter<AccountEntity, U> andThen(Converter<? super Account, ? extends U> after) {
        return Converter.super.andThen(after);
    }


}
