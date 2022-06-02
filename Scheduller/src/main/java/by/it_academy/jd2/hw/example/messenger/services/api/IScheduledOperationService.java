package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledOperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IScheduledOperationService {
    ScheduledOperation get(UUID uuid);
    ScheduledOperation save(ScheduledOperation scheduledOperation);
    Page<ScheduledOperation> getAll(Pageable pageable);
    ScheduledOperation update (UUID uuid, ScheduledOperation scheduledOperation);
}
