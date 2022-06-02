package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IOperationService {
    Operation get(UUID uuid);
    Operation save(Operation operationE);
    Page<Operation> getAll(Pageable pageable);
    Operation update (UUID uuid, Operation operationRaw);
}
