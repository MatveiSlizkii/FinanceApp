package by.it_academy.jd2.hw.example.messenger.dao.api;

import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduledReportStorage  extends JpaRepository<ScheduledReportEntity, UUID> {
}
