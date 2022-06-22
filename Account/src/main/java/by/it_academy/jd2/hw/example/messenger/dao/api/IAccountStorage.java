package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountStorage extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUserAndUuid(String login, UUID uuid);
    Optional<AccountEntity> findByUserAndTitle(String login, String title);
    List<AccountEntity> findAllByUser (String login);
    boolean existsAccountEntityByUserAndUuid(String login, UUID uuidAccount);

}
