package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IScheduleStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduleEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduleService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService implements IScheduleService {
    private final IScheduleStorage scheduleStorage;
    private final ConversionService conversionService;
    @PersistenceContext
    private EntityManager em;

    public ScheduleService(IScheduleStorage scheduleStorage,
                           ConversionService conversionService) {
        this.scheduleStorage = scheduleStorage;
        this.conversionService = conversionService;
    }

    @Override
    public Schedule get(UUID uuid) {

        return conversionService.convert(scheduleStorage.getById(uuid), Schedule.class);
    }

    @Override
    public Schedule save(Schedule schedule) {
        //TODO чек на валидность шедуля
        LocalDateTime localDateTime = LocalDateTime.now();
        schedule.setUuid(UUID.randomUUID());
        schedule.setDt_create(localDateTime);
        schedule.setDt_update(localDateTime);
        ScheduleEntity scheduleEntity = conversionService.convert(schedule, ScheduleEntity.class);
        return conversionService.convert(scheduleStorage.save(scheduleEntity), Schedule.class);
    }

    @Override
    public Page<Schedule> getAll(Pageable pageable) {
        //TODO как-то перебить череч JWT
        List<Schedule> schedules = new ArrayList<>();
        scheduleStorage.findAll().forEach((o) ->
                schedules.add(conversionService.convert(o, Schedule.class)));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), schedules.size());
        return new PageImpl<>(schedules.subList(start, end), pageable, schedules.size());
    }

    @Override
    public Schedule update(UUID uuid, Schedule scheduleRaw) {
        //TODO чек шедуля
        //TODO чек уида
        ScheduleEntity scheduleEntity = em.find(ScheduleEntity.class, uuid);
        em.refresh(scheduleEntity, LockModeType.OPTIMISTIC);
        //TODO переписать на норм варик
        //TODO переписать с помощью удаления старого?????
        if (scheduleRaw.getStart_time() != null) {
            scheduleEntity.setStartTime(scheduleRaw.getStart_time());
        }
        if (scheduleRaw.getStop_time() != null) {
            scheduleEntity.setStopTime(scheduleRaw.getStop_time());
        }
        if (scheduleRaw.getInterval() != null) {
            scheduleEntity.setInterval(scheduleRaw.getInterval());
        }
        if (scheduleRaw.getTime_unit() != null) {
            scheduleEntity.setTime_unit(scheduleRaw.getTime_unit().name());
        }


        return conversionService.convert(scheduleEntity, Schedule.class);
    }

}
