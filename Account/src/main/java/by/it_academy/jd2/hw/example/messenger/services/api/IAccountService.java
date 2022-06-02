package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.dao.entity.BalanceEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAccountService {
    Account get(UUID uuid);
    Account save(Account account);
    Page<Account> getAll(Pageable pageable);
    Account update (UUID uuid, Account account, Long dt_update);
    BalanceEntity updateBalance (UUID uuid, Double value);
}
