package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.model.Account;
import by.it_academy.jd2.hw.example.messenger.model.Operation;
import by.it_academy.jd2.hw.example.messenger.services.api.IDataReport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataReport implements IDataReport {
    private final RestTemplate restTemplate;

    public DataReport() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Map<UUID, String> getMapCurrency() {
        ResponseEntity<Collection> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/api/classifier/close/currency",
                        Collection.class);
        Collection collectionCurrencyRaw = response.getBody();
        List<Map<String, String>> listCurrencyRaw2 = (List<Map<String, String>>) collectionCurrencyRaw;
        Map<UUID, String> mapCurrency = new HashMap<>();
        listCurrencyRaw2.forEach((o) ->
                mapCurrency.put(UUID.fromString(o.get("uuid")), o.get("title")));
        return mapCurrency;
    }

    @Override
    public Account getAccounts(UUID accountUuid) {

        ResponseEntity<Map> response3 =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/account/" + accountUuid,
                        Map.class);
        Map<String, Object> raw = response3.getBody();
        Account account = Account.Builder.createBuilder()
                .setUuid(accountUuid)
                .setTitle(raw.get("title").toString())
                .setDescription(raw.get("description").toString())
                .setType(raw.get("type").toString())
                .setCurrency(UUID.fromString(raw.get("currency").toString()))
                .setBalance(Double.parseDouble(raw.get("balance").toString()))
                .build();


        return account;
    }

    @Override
    public Map<UUID, String> getMapOperationCategory() {
        ResponseEntity<Collection> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/api/classifier/close/operation",
                        Collection.class);
        Collection raw = response.getBody();
        List<Map<String, String>> listOperationCategory = (List<Map<String, String>>) raw;
        Map<UUID, String> mapOperationCategory = new HashMap<>();
        listOperationCategory.forEach((o) ->
                mapOperationCategory.put(UUID.fromString(o.get("uuid")), o.get("title")));

        return mapOperationCategory;
    }

    @Override
    public List<Operation> getOperations(UUID accountUuid, LocalDateTime to, LocalDateTime from) {

        Long toLong = to.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(to)).toEpochMilli();
        Long fromLong = from.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(from)).toEpochMilli();

        Map<UUID, String> nameOperationCategory = getMapOperationCategory();
        List<Operation> operationList = new ArrayList<>();

        ResponseEntity<Collection> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/account/close?to=" + toLong + "&from=" + fromLong + "&uuid=" + accountUuid,
                        Collection.class);
        Collection raw = response.getBody();

        List<Map<String, Object>> operationRaw = (List<Map<String, Object>>) raw;

        for (Map<String, Object> o : operationRaw) {
            String operationCategory = nameOperationCategory.get(UUID.fromString(o.get("category").toString()));
            Operation operation = Operation.Builder.createBuilder()
                    .setDescription(o.get("description").toString())
                    .setCategory(operationCategory)
                    .setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) o.get("date")),
                            TimeZone.getDefault().toZoneId()))
                    .setValue((Double) o.get("value"))
                    .build();
            operationList.add(operation);
        }

        return operationList;
    }
}
