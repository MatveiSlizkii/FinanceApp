package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IScheduledOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledOperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduleService;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import by.it_academy.jd2.hw.example.messenger.services.api.ISchedulerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ScheduledOperationService implements IScheduledOperationService {
    private final IScheduledOperationStorage scheduledOperationStorage;
    private final ConversionService conversionService;
    private final IScheduleService scheduleService;
    private final IOperationService operationService;
    private final ISchedulerService schedulerService;

    @PersistenceContext
    private EntityManager em;

    public ScheduledOperationService(IScheduledOperationStorage scheduledOperationStorage,
                                     ConversionService conversionService,
                                     IScheduleService scheduleService,
                                     IOperationService operationService,
                                     ISchedulerService schedulerService) {
        this.scheduledOperationStorage = scheduledOperationStorage;
        this.conversionService = conversionService;
        this.scheduleService = scheduleService;
        this.operationService = operationService;
        this.schedulerService = schedulerService;
    }

    @Override
    public ScheduledOperation get(UUID uuid) {
        return conversionService.convert(scheduledOperationStorage.getById(uuid), ScheduledOperation.class);
    }

    @Override
    @Transactional
    public ScheduledOperation save(ScheduledOperation scheduledOperation) {
        Operation operation = operationService.save(scheduledOperation.getOperation());
        scheduleService.save(scheduledOperation.getSchedule());
        LocalDateTime localDateTime = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        scheduledOperation.setUuid(uuid);
        scheduledOperation.setDt_create(localDateTime);
        scheduledOperation.setDt_update(localDateTime);
        scheduledOperation.getOperation().setDt_create(operation.getDt_create());
        scheduledOperationStorage.save(conversionService.convert(scheduledOperation, ScheduledOperationEntity.class));
       this.schedulerService.create(scheduledOperation);
        return scheduledOperation;
    }

    @Override
    public Page<ScheduledOperation> getAll(Pageable pageable) {
        List<ScheduledOperation> scheduledOperations = new ArrayList<>();
        scheduledOperationStorage.findAll().forEach((o)->
                scheduledOperations.add(conversionService.convert(o, ScheduledOperation.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), scheduledOperations.size());
        return new PageImpl<>(scheduledOperations.subList(start, end), pageable, scheduledOperations.size());
    }

    @Override
    public ScheduledOperation update(UUID uuid, ScheduledOperation scheduledOperationRaw) {
        ScheduledOperationEntity scheduledOperationEntity = em.find(ScheduledOperationEntity.class, uuid);
        em.refresh(scheduledOperationEntity, LockModeType.OPTIMISTIC);
        /*
                this.scheduleEntity = scheduleEntity;
        this.operationEntity = operationEntity;
         */
        if (scheduledOperationRaw.getSchedule() != null) {
            scheduledOperationEntity.setSchedule(scheduledOperationRaw.getSchedule().getUuid());
        }
        if (scheduledOperationRaw.getOperation() != null) {
            scheduledOperationEntity.setOperation(scheduledOperationRaw.getOperation().getUuid());
        }

        em.close(); // под вопросом надо ли она, скорее всего нет

        return conversionService.convert(scheduledOperationEntity, ScheduledOperation.class);
    }


}
