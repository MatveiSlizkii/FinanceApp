package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ISchedulerService {

    void create (ScheduledOperation scheduledOperation);
    void update (UUID uuidOperation, ScheduledOperation scheduledOperation);
}
