package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
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
public class OperationService implements IOperationService {
    private final IOperationStorage operationStorage;
    private final ConversionService conversionService;
    private final IAccountService accountService;

    @PersistenceContext
    private EntityManager em;

    public OperationService(IOperationStorage operationStorage,
                            ConversionService conversionService,
                            IAccountService accountService) {
        this.operationStorage = operationStorage;
        this.conversionService = conversionService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public Operation get(UUID uuid) {
        return conversionService.convert(operationStorage.findByUuidAccount(uuid),Operation.class);
    }

    @Override
    @Transactional
    public Operation save(Operation operation) {
        LocalDateTime timeNow = LocalDateTime.now();
//        IValidateArgument<Operation> validateArgument = new ValidateOperation();
//        validateArgument.validate(operation);
        operation.setUuid(UUID.randomUUID());
        operation.setDtCreate(timeNow);
        operation.setDtUpdate(timeNow);
        accountService.updateBalance(operation.getUuidAccount(), operation.getValue());

        OperationEntity operationEntity = conversionService.convert(operation, OperationEntity.class);
        return conversionService.convert(operationStorage.save(operationEntity),Operation.class);
    }

    @Override
    @Transactional
    public Page<Operation> getAll(Pageable pageable) {
        List<Operation> operations= new ArrayList<>();
        operationStorage.findAll().forEach((o)->
                operations.add(conversionService.convert(o, Operation.class)));

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }

    @Override
    @Transactional
    public Page<Operation> getByUuidAccount(UUID uuidAccount, Pageable pageable) {
        List<Operation> operations = new ArrayList<>();
        operationStorage.findByUuidAccount(uuidAccount).forEach((o)->
                operations.add(conversionService.convert(o, Operation.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }

    @Override
    @Transactional
    public Operation update(UUID uuid, Operation operationRaw, Long dt_update) {
        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);

        if (!operationStorage.getById(uuid).getDtUpdate().equals(checkDateTime)) {
            throw new IllegalArgumentException("Данная версия для обновления устарела," +
                    "обновите, пожалуйста страницу");
        }
        OperationEntity operationEntity = em.find(OperationEntity.class, uuid);
        em.refresh(operationEntity, LockModeType.OPTIMISTIC);

        Double valueFinal = operationRaw.getValue() - operationEntity.getValue();
        if (operationRaw.getDate() != null) {
            operationEntity.setDate(operationRaw.getDtCreate());
        }
        if (operationRaw.getDescription() != null) {
            operationEntity.setDescription(operationRaw.getDescription());
        }
        if (operationRaw.getCategory() != null) {
            operationEntity.setCategory(operationRaw.getCategory());
        }
        if (operationRaw.getValue() != null) {
            operationEntity.setValue(operationRaw.getValue());
        }
        if (operationRaw.getCurrency() != null) {
            operationEntity.setCurrency(operationRaw.getCurrency());
        }

        accountService.updateBalance(operationEntity.getUuidAccount(), valueFinal);

        em.close(); // под вопросом надо ли она, скорее всего нет

        return conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    @Transactional
    public Operation delete(UUID uuid, Long dt_update) {

        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);

        if (!operationStorage.getById(uuid).equals(checkDateTime)) {
            throw new IllegalArgumentException("Данная версия для обновления устарела," +
                    "обновите, пожалуйста страницу");
        }

        OperationEntity operationEntity = em.find(OperationEntity.class, uuid);
        em.refresh(operationEntity, LockModeType.OPTIMISTIC);
        operationStorage.delete(operationEntity);
        //em.close(); // под вопросом надо ли она, скорее всего нет
        return conversionService.convert(operationEntity,Operation.class);
    }

    @Override
    public List<Operation> getBetweenDates(LocalDateTime to, LocalDateTime from, UUID uuidAccount) {
        List<Operation> operations = new ArrayList<>();
        List<OperationEntity> operationEntities = operationStorage.findByUuidAccountAndDateBetween(uuidAccount, to, from);
        operationEntities.forEach((o)->
                operations.add(conversionService.convert(o, Operation.class)));
        return operations;
    }
}
