package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationCategoryStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationCategoryService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OperationCategoryService implements IOperationCategoryService {
    private final IOperationCategoryStorage operationCategoryStorage;
    private final ConversionService conversionService;

    public OperationCategoryService(IOperationCategoryStorage operationCategoryStorage,
                                    ConversionService conversionService) {
        this.operationCategoryStorage = operationCategoryStorage;
        this.conversionService = conversionService;
    }

    @Override
    public OperationCategory get(UUID uuid) {
        OperationCategoryEntity operationCategoryEntity;

        try {
            operationCategoryEntity = operationCategoryStorage.findById(uuid).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(MessageError.SQL_ERROR, e);
        }

        if (operationCategoryEntity == null) {
            throw new ValidationException(MessageError.ID_NOT_EXIST);
        }


        return conversionService.convert(operationCategoryStorage.getById(uuid), OperationCategory.class);
    }

    @Override
    public OperationCategory save(OperationCategory operationCategory) {

        if (operationCategory == null || operationCategory.getTitle() == null || operationCategory.getTitle().isEmpty()) {
            throw new ValidationException(
                    new ValidationError("title (название категории)", MessageError.MISSING_FIELD));

        } else if (this.operationCategoryStorage.findByTitle(operationCategory.getTitle()).isPresent()) {
            throw new ValidationException(
                    new ValidationError("title (название категории)", MessageError.NO_UNIQUE_FIELD));
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        operationCategory.setUuid(UUID.randomUUID());
        operationCategory.setDt_create(localDateTime);
        operationCategory.setDt_update(localDateTime);

        OperationCategoryEntity operationCategoryEntity = conversionService.convert(operationCategory, OperationCategoryEntity.class);
        operationCategoryStorage.save(operationCategoryEntity);
        return operationCategory;
    }

    @Override
    public Page<OperationCategory> getAll(Pageable pageable) {
        List<OperationCategory> operationCategories = new ArrayList<>();
        operationCategoryStorage.findAll().forEach((o)->
                operationCategories.add(conversionService.convert(o, OperationCategory.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), operationCategories.size());
        return new PageImpl<>(operationCategories.subList(start, end), pageable, operationCategories.size());
    }

    @Override
    public List<OperationCategory> getAllList() {
        List <OperationCategoryEntity> operationCategoryEntities = operationCategoryStorage.findAll();
        List <OperationCategory> operationCategories = new ArrayList<>();

        operationCategoryEntities.forEach((o)->
                operationCategories.add(conversionService.convert(o, OperationCategory.class)));
        return operationCategories;
    }
}
