package by.it_academy.jd2.hw.example.messenger.services.api;


import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;

import java.util.UUID;

public interface ISchedulerService {
    void create(ScheduledReport ScheduledReport);
    void stop(UUID uuid);
    //TODO сделать обновление шедулера
}
