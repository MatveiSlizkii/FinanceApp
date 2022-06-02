package by.it_academy.jd2.hw.example.messenger.services.api;


import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IOperationCategoryService {
    OperationCategory get(UUID id);
    OperationCategory save(OperationCategory operationCategory);
    Page<OperationCategory> getAll(Pageable pageable);
    List<OperationCategory> getAllList();
}
