package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.entity.OperationEntity;
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
    @PersistenceContext
    private EntityManager em;

    public OperationService(IOperationStorage operationStorage, ConversionService conversionService) {
        this.operationStorage = operationStorage;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public Operation get(UUID id) {
        return conversionService.convert(operationStorage.getById(id), Operation.class);
    }

    @Override
    @Transactional
    public Operation save(Operation operation) {
//        IValidateArgument<OperationEntity> validateArgument = new ValidateOperation();
//        validateArgument.validate(operationEntity);
        operation.setUuid(UUID.randomUUID());
        operation.setDt_create(LocalDateTime.now());
        operation.setDt_update(LocalDateTime.now());

        operationStorage.save(conversionService.convert(operation, OperationEntity.class));
        return operation;
    }

    @Override
    @Transactional
    public Page<Operation> getAll(Pageable pageable) {
        List<Operation> operations = new ArrayList<>();
        operationStorage.findAll().forEach((o)->
                operations.add(conversionService.convert(o, Operation.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }


    @Override
    @Transactional
    public Operation update(UUID uuid, Operation operationRaw) {
        OperationEntity operationEntity = em.find(OperationEntity.class, uuid);
        em.refresh(operationEntity, LockModeType.OPTIMISTIC);

        if (operationRaw.getAccount() != null) {
            operationEntity.setAccount(operationRaw.getAccount());
        }
        if (operationRaw.getDescription() != null) {
            operationEntity.setDescription(operationRaw.getDescription());
        }
        if (operationRaw.getValue() != null) {
            operationEntity.setValue(operationRaw.getValue());
        }
        if (operationRaw.getCurrency() != null) {
            operationEntity.setCurrency(operationRaw.getCurrency());
        }
        if (operationRaw.getCategory() != null) {
            operationEntity.setCategory(operationRaw.getCategory());
        }
        em.close(); // под вопросом надо ли она, скорее всего нет

        return conversionService.convert(operationEntity, Operation.class);
    }


}
