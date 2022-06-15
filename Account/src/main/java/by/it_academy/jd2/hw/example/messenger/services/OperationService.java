package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OperationService implements IOperationService {
    private final IOperationStorage operationStorage;
    private final ConversionService conversionService;
    private final IAccountService accountService;
    private final EntityManager em;
    private final RestTemplate restTemplate;

    public OperationService(IOperationStorage operationStorage,
                            ConversionService conversionService,
                            IAccountService accountService,
                            EntityManager em) {
        this.operationStorage = operationStorage;
        this.conversionService = conversionService;
        this.accountService = accountService;
        this.restTemplate = new RestTemplate();
        this.em = em;
    }
    @Value("${classifier_currency_url}")
    private String currencyUrl;

    @Value("${classifier_category_url}")
    private String categoryUrl;

    @Override
    @Transactional
    public Operation get(UUID uuid) {
        //TODO навесить трай кетч существует ли операция
        return conversionService.convert(operationStorage.findByUuidAccount(uuid), Operation.class);
    }

    @Override
    @Transactional
    public Operation save(Operation operation) {
        LocalDateTime timeNow = LocalDateTime.now();
        operation.setUuid(UUID.randomUUID());
        operation.setDtCreate(timeNow);
        operation.setDtUpdate(timeNow);
        accountService.updateBalance(operation.getUuidAccount(), operation.getValue());

        OperationEntity operationEntity = conversionService.convert(operation, OperationEntity.class);
        return conversionService.convert(operationStorage.save(operationEntity), Operation.class);
    }

    @Override
    @Transactional
    public Page<Operation> getAll(Pageable pageable) {
        List<Operation> operations = new ArrayList<>();
        operationStorage.findAll().forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }

    @Override
    @Transactional
    public Page<Operation> getByUuidAccount(UUID uuidAccount, Pageable pageable) {
        List<Operation> operations = new ArrayList<>();
        operationStorage.findByUuidAccount(uuidAccount).forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));
        int start = (int) pageable.getOffset();
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
        return conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public List<Operation> getBetweenDates(LocalDateTime to, LocalDateTime from, UUID uuidAccount) {
        List<Operation> operations = new ArrayList<>();
        List<OperationEntity> operationEntities = operationStorage.findByUuidAccountAndDateBetween(uuidAccount, to, from);
        operationEntities.forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));
        return operations;
    }


    private void checkOperation(Operation operation, List<ValidationError> errors) {

        if (operation == null) {
            errors.add(new ValidationError("operation", "Не передан объект operation"));
            return;
        }

        String currencyClassifierUrl = currencyUrl + operation.getCurrency() + "/";
        String categoryClassifierUrl = categoryUrl + operation.getCategory() + "/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        if (operation.getCategory() == null) {
            errors.add(new ValidationError("category", "Не передана категория операции"));
        } else {
            try {
                this.restTemplate.exchange(categoryClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("category", "Передан uuid категории, которой нет в справочнике"));
            }
        }

        if (operation.getValue() == 0) {
            errors.add(new ValidationError("value", "Передана нулевая сумма операции"));
        }

        if (operation.getCurrency() == null) {
            errors.add(new ValidationError("currency", "Не передана валюта операции"));
        } else {
            try {
                this.restTemplate.exchange(currencyClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("currency", "Передан uuid валюты, которого нет в справочнике"));
            }
        }

        if (operation.getUuidAccount() == null){
            errors.add(new ValidationError("UuidAccount", "Не передан uuid счёта"));
        } else

            //TODO проверить как я сделал, верный ли кетч
            try {
                accountService.get(operation.getUuidAccount());
            }
            catch (IllegalArgumentException e){
            errors.add(new ValidationError("UuidAccount", "Передан uuid несуществующего счёта"));
        }
        }
    }

