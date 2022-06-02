package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.errors.ArgumentError;
import by.it_academy.jd2.hw.example.messenger.errors.ArgumentException;
import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IValidate;

public class ValidateOperationCategory implements IValidate<OperationCategoryEntity> {
    @Override
    public void validate(OperationCategoryEntity operationCategoryEntity) {
        if (operationCategoryEntity.getTitle().isEmpty()){
            ArgumentError argumentError = new ArgumentError("Title",
                    "Не передано название категории");
            throw new ArgumentException("Ошибка создания типа затрат", argumentError);
        }
    }
}
