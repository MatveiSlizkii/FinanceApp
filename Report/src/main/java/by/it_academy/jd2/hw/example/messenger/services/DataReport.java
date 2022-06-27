package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest.utils.JwtTokenUtil;
import by.it_academy.jd2.hw.example.messenger.model.Account;
import by.it_academy.jd2.hw.example.messenger.model.Operation;
import by.it_academy.jd2.hw.example.messenger.services.api.IDataReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataReport implements IDataReport {
    private RestTemplate restTemplate;
    private final UserHolder userHolder;

    public DataReport(UserHolder userHolder) {
        this.restTemplate = new RestTemplate();
        this.userHolder = userHolder;

    }

    @Value("${classifier_currency_url}")
    private String currencyUrl = "http://localhost:8081/api/classifier/close/currency/";

    @Value("${classifier_category_url}")
    private String categoryUrl = "http://localhost:8081/api/classifier/close/currency/";

    @Value("${account_url}")
    private String accountUrl = "http://localhost:8080/api/account/";
    @Value("${account_to_from_url}")
    private String accountToFromUrl = "http://localhost:8080/account/close/tofrom";

    @Override
    public Map<UUID, String> getMapCurrency() {
        System.out.println(currencyUrl);
        ResponseEntity<Collection> response =
                restTemplate.getForEntity(
                        currencyUrl,
                        Collection.class);
        Collection collectionCurrencyRaw = response.getBody();
        List<Map<String, String>> listCurrencyRaw2 = (List<Map<String, String>>) collectionCurrencyRaw;
        Map<UUID, String> mapCurrency = new HashMap<>();
        listCurrencyRaw2.forEach((o) ->
                mapCurrency.put(UUID.fromString(o.get("uuid")), o.get("title")));
        return mapCurrency;
    }

    @Override
    public Account getAccount(UUID accountUuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        String token = JwtTokenUtil.generateAccessToken(this.userHolder.getUser());
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        ResponseEntity<Map> response3 =
                restTemplate.exchange(
                        accountUrl + accountUuid, HttpMethod.GET, entity,
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
                        categoryUrl,
                        Collection.class);
        Collection raw = response.getBody();
        List<Map<String, String>> listOperationCategory = (List<Map<String, String>>) raw;
        Map<UUID, String> mapOperationCategory = new HashMap<>();
        listOperationCategory.forEach((o) ->
                mapOperationCategory.put(UUID.fromString(o.get("uuid")), o.get("title")));

        return mapOperationCategory;
    }

    @Override
    public List<Operation> getOperations(UUID accountUuid, Long to, Long from) {



        Map<UUID, String> nameOperationCategory = this.getMapOperationCategory();
        List<Operation> operationList = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        String token = JwtTokenUtil.generateAccessToken(this.userHolder.getUser());
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        ResponseEntity<Collection> response =
                restTemplate.exchange(
                        accountToFromUrl + "?to=" + to + "&from=" + from + "&uuid=" + accountUuid, HttpMethod.GET, entity,
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
