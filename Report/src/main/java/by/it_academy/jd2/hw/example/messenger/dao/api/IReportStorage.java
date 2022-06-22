package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.dao.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IReportStorage extends JpaRepository<ReportEntity, UUID> {
    ReportEntity findByUuidAndUser (UUID uuid, String login);
    List<ReportEntity> findAllByUser (String login);
}
