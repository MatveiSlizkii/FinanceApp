package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface IOperationStorage extends JpaRepository<OperationEntity, UUID> {
    List<OperationEntity> findByUuidAccount(UUID uuidAccount);
    List<OperationEntity> findByUuidAccountAndDateBetween(UUID uuidAccount, LocalDateTime to, LocalDateTime from);
}
