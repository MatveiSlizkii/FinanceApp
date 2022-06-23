package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entity.ScheduledOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IScheduledOperationStorage extends JpaRepository<ScheduledOperationEntity, UUID> {
    List<ScheduledOperationEntity> findAllByUser (String login);



}
