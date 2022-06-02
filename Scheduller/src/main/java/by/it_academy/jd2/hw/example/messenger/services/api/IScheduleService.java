package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IScheduleService {
    Schedule get(UUID uuid);
    Schedule save(Schedule schedule);
    Page<Schedule> getAll(Pageable pageable);
    Schedule update (UUID uuid, Schedule scheduleRaw);
}
