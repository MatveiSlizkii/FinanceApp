package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IOperationService {
    Operation get(UUID uuid);
    Operation save(Operation operation);
    Page<Operation> getAll(Pageable pageable);
    Page<Operation> getByUuidAccount(UUID uuidAccount, Pageable pageable);
    Operation update (UUID uuid, Operation operationRaw, Long dt_update);
    Operation delete (UUID uuid, Long dt_update);
    List<Operation> getBetweenDates (LocalDateTime to, LocalDateTime from, UUID uuidAccount);
}
