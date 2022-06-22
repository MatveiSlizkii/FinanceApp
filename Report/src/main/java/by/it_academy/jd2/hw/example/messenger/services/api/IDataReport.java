package by.it_academy.jd2.hw.example.messenger.services.api;

import by.it_academy.jd2.hw.example.messenger.model.Account;
import by.it_academy.jd2.hw.example.messenger.model.Operation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IDataReport {

    Map<UUID, String> getMapCurrency();
    Account getAccount(UUID accountUuid);
    Map<UUID, String> getMapOperationCategory();
    List<Operation> getOperations (UUID accountUuid, Long to, Long from);

}
