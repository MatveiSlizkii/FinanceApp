package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IOperationCategoryStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationCategoryService;
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
    public OperationCategory get(UUID id) {
        return conversionService.convert(operationCategoryStorage.getById(id), OperationCategory.class);
    }

    @Override
    public OperationCategory save(OperationCategory operationCategory) {
//        IValidate<OperationCategoryEntity> validate = new ValidateOperationCategory();
//        validate.validate(operationCategoryEntity);
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
