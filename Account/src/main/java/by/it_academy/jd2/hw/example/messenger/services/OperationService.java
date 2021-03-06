package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.utils.JwtTokenUtil;
import by.it_academy.jd2.hw.example.messenger.services.api.*;
import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private final UserHolder userHolder;

    public OperationService(IOperationStorage operationStorage,
                            ConversionService conversionService,
                            IAccountService accountService,
                            EntityManager em,
                            UserHolder userHolder) {
        this.operationStorage = operationStorage;
        this.conversionService = conversionService;
        this.accountService = accountService;
        this.restTemplate = new RestTemplate();
        this.em = em;
        this.userHolder = userHolder;
    }

    @Value("${classifier_currency_url}")
    private String currencyUrl;

    @Value("${classifier_category_url}")
    private String categoryUrl;

    @Override
    @Transactional
    public Operation get(UUID uuid) {
        String login = userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        if (!accountService.checkAccountByUser(operationStorage.getById(uuid).getUuidAccount(), login)) {
            errors.add(new ValidationError("operation", "???????????? ???????????????? ???? ???????????????????? ?????? ?????? ??????????????"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        return conversionService.convert(operationStorage.findByUuidAccount(uuid), Operation.class);
    }

    @Override
    @Transactional
    public Operation save(Operation operation) {
        List<ValidationError> errors = new ArrayList<>();
        this.checkOperation(operation, errors);
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
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
        String login = userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();

        List<UUID> uuidsAccounts = accountService.uuidsAccountsByUser(login);
        List<OperationEntity> operationEntities = new ArrayList<>();

        uuidsAccounts.forEach((o) ->
                operationEntities.addAll(operationStorage.findByUuidAccount(o)));
        List<Operation> operations = new ArrayList<>();
        operationEntities.forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));

        if (operations.isEmpty()) {
            errors.add(new ValidationError("operation", "?? ?????? ?????? ?????? ???????????????? ???? ?????????? ????????????"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }

    @Override
    @Transactional
    public Page<Operation> getByUuidAccount(UUID uuidAccount, Pageable pageable) {
        String login = this.userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        if (!accountService.checkAccountByUser(uuidAccount, login)) {
            errors.add(new ValidationError("user", MessageError.ID_NOT_EXIST));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        List<Operation> operations = new ArrayList<>();
        operationStorage.findByUuidAccount(uuidAccount).forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operations.size());
        return new PageImpl<>(operations.subList(start, end), pageable, operations.size());
    }

    @Override
    @Transactional
    public Operation update(UUID uuidOperation, Operation operationRaw, Long dt_update) {
        String login = this.userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        this.checkOperation(operationRaw, errors);
        if (!accountService.checkAccountByUser(operationRaw.getUuidAccount(), login)) {
            errors.add(new ValidationError("user", MessageError.ID_NOT_EXIST));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        LocalDateTime checkDateTime = null;
        try {
            checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);
        } catch (Exception e) {
            throw new ValidationException("dt_update" + MessageError.INVALID_FORMAT);
        }

        if (!operationStorage.getById(uuidOperation).getDtUpdate().equals(checkDateTime)) {
            throw new IllegalArgumentException("???????????? ???????????? ?????? ???????????????????? ????????????????," +
                    "????????????????, ???????????????????? ????????????????");
        }
        OperationEntity operationEntity = em.find(OperationEntity.class, uuidOperation);
        System.out.println(operationEntity);
        em.refresh(operationEntity, LockModeType.OPTIMISTIC);
        if (!accountService.checkAccountByUser(operationEntity.getUuidAccount(), login)) {
            errors.add(new ValidationError("user", MessageError.ID_NOT_EXIST));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        Double valueFinal = operationRaw.getValue() - operationEntity.getValue();

        operationEntity.setDate(operationRaw.getDate());
        operationEntity.setDescription(operationRaw.getDescription());
        operationEntity.setCategory(operationRaw.getCategory());
        operationEntity.setValue(operationRaw.getValue());
        operationEntity.setCurrency(operationRaw.getCurrency());


        accountService.updateBalance(operationEntity.getUuidAccount(), valueFinal);

        System.out.println(operationEntity);
        return conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    @Transactional
    public Operation delete(UUID uuid, Long dt_update) {
        String login = this.userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        if (!accountService.checkAccountByUser(operationStorage.getById(uuid).getUuidAccount(), login)) {
            errors.add(new ValidationError("user", MessageError.ID_NOT_EXIST));
        }
        LocalDateTime checkDateTime = conversionService.convert(dt_update, LocalDateTime.class);

        if (!operationStorage.getById(uuid).equals(checkDateTime)) {
            errors.add(new ValidationError("dt_update", MessageError.INVALID_DT_UPDATE));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }

        OperationEntity operationEntity = em.find(OperationEntity.class, uuid);
        em.refresh(operationEntity, LockModeType.OPTIMISTIC);
        operationStorage.delete(operationEntity);
        return conversionService.convert(operationEntity, Operation.class);
    }

    @Override
    public List<Operation> getBetweenDates(LocalDateTime to, LocalDateTime from, UUID uuidAccount) {
        String login = this.userHolder.getLoginFromContext();
        List<ValidationError> errors = new ArrayList<>();
        if (!accountService.checkAccountByUser(uuidAccount, login)) {
            errors.add(new ValidationError("user", MessageError.ID_NOT_EXIST));
        }
        List<Operation> operations = new ArrayList<>();
        List<OperationEntity> operationEntities = operationStorage.findByUuidAccountAndDateBetween(uuidAccount, to, from);

        if (!errors.isEmpty()) {
            throw new ValidationException("???????????????? ???????????????????????? ??????????????????", errors);
        }
        operationEntities.forEach((o) ->
                operations.add(conversionService.convert(o, Operation.class)));
        return operations;
    }


    private void checkOperation(Operation operation, List<ValidationError> errors) {

        String login = userHolder.getLoginFromContext();
        if (operation == null) {
            errors.add(new ValidationError("operation", "???? ?????????????? ???????????? operation"));
            return;
        }

        String currencyClassifierUrl = currencyUrl + operation.getCurrency() + "/";
        String categoryClassifierUrl = categoryUrl + operation.getCategory() + "/";
        HttpHeaders headers = new HttpHeaders();
        String token = JwtTokenUtil.generateAccessToken(this.userHolder.getUser());
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        if (operation.getCategory() == null) {
            errors.add(new ValidationError("category", "???? ???????????????? ?????????????????? ????????????????"));
        } else {
            try {
                this.restTemplate.exchange(categoryClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("category", "?????????????? uuid ??????????????????, ?????????????? ?????? ?? ??????????????????????"));
            }
        }

        if (operation.getValue() == 0) {
            errors.add(new ValidationError("value", "???????????????? ?????????????? ?????????? ????????????????"));
        }

        if (operation.getCurrency() == null) {
            errors.add(new ValidationError("currency", "???? ???????????????? ???????????? ????????????????"));
        } else {
            try {
                this.restTemplate.exchange(currencyClassifierUrl, HttpMethod.GET, entity, String.class);
            } catch (HttpStatusCodeException e) {
                errors.add(new ValidationError("currency", "?????????????? uuid ????????????, ???????????????? ?????? ?? ??????????????????????"));
            }
        }

        if (operation.getUuidAccount() == null) {
            errors.add(new ValidationError("UuidAccount", "???? ?????????????? uuid ??????????"));
        } else {

            try {
                accountService.get(operation.getUuidAccount());
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError("UuidAccount", "?????????????? uuid ?????????????????????????????? ??????????"));
            }
            if (!accountService.checkAccountByUser(operation.getUuidAccount(), login)) {
                errors.add(new ValidationError("UuidAccount", "?????????????? uuid  ?? ???????????????? ???? ???? ???????????? ??????????????"));
            }

        }


    }
}

