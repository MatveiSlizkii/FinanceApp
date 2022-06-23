package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.api.IBalanceStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entity.BalanceEntity;
import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/account/close")
public class RestCloseController {
    private final ConversionService conversionService;
    private final IBalanceStorage balanceStorage;
    private final IAccountService accountService;
    private final IOperationService operationService;

    public RestCloseController(ConversionService conversionService,
                               IAccountService accountService,
                               IOperationService operationService,
                               IBalanceStorage balanceStorage) {
        this.conversionService = conversionService;
        this.accountService = accountService;
        this.operationService = operationService;
        this.balanceStorage = balanceStorage;
    }

    @RequestMapping(
            value = {"/insertbd", "/insertbd/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Account create() {
        Random rand = new Random();
        LocalDateTime localDateTime = LocalDateTime.now();
        List<String> categoryString = new ArrayList<>();
        categoryString.add("d938418c-58ff-4376-897c-1b904d1dc2ed");
        categoryString.add("7670f360-ae8e-467c-a1c9-12f3486b0f9c");
        categoryString.add("12fd0af2-5a20-49ee-b6e9-6c69f8ce4dcd");
        categoryString.add("44b44a96-1357-4f6a-8e27-ad0efd586c3e");
        categoryString.add("950a3980-6745-437b-9bc2-c7a585b87051");
        categoryString.add("bbcd9d0d-a0c1-4151-9763-59ba353eea3a");
        categoryString.add("8e6b08bc-2dce-42a9-81eb-7dd878b1544c");
        categoryString.add("673fb577-bf5a-44b4-ae73-e201d1a6013c");
        categoryString.add("9156e4d5-56ca-482a-bfb2-91daafc65f5c");
        categoryString.add("5d990d34-47b6-4a56-8474-8dc249c21497");
        List<UUID> categoryUUID = new ArrayList<>();
        categoryString.forEach((o) ->
                categoryUUID.add(UUID.fromString(o)));


        for (int i = 0; i < 3000; i++) {
            double value = Math.random() * 100 - 50;
            double scale = Math.pow(10, 2);
            double result = Math.ceil(value * scale) / scale;
            String description = "Описание " + i;
            System.out.println("Заполнение " + i);

            //счет в российских
            Operation operation = Operation.Builder.createBuilder()
                    .setDate(localDateTime.minus(i * 8, ChronoUnit.HOURS))
                    .setDescription(description)
                    .setCategory(categoryUUID.get(rand.nextInt(categoryUUID.size())))
                    .setValue(result)
                    .setCurrency(UUID.fromString("d0bd04fb-6fff-4b66-973a-9d3aa856d6ed"))
                    .setUuidAccount(UUID.fromString("ab4fd3b1-b951-4c26-b373-1f5cab202f00"))
                    .build();
            operationService.save(operation);
            //белки
            double value1 = Math.random() * 100 - 50;
            double scale1 = Math.pow(10, 2);
            double result1 = Math.ceil(value1 * scale1) / scale1;
            Operation operation1 = Operation.Builder.createBuilder()
                    .setDate(localDateTime.minus(i * 8, ChronoUnit.HOURS))
                    .setDescription(description)
                    .setCategory(categoryUUID.get(rand.nextInt(categoryUUID.size())))
                    .setValue(result1)
                    .setCurrency(UUID.fromString("6dc44b27-52d9-498d-a5af-31b604351b9f"))
                    .setUuidAccount(UUID.fromString("766b6870-12d7-4189-a42f-b98776519016"))
                    .build();
            operationService.save(operation1);
            //вечнозеленые
            double value2 = Math.random() * 100 - 50;
            double scale2 = Math.pow(10, 2);
            double result2 = Math.ceil(value2 * scale2) / scale2;
            Operation operation2 = Operation.Builder.createBuilder()
                    .setDate(localDateTime.minus(i * 8, ChronoUnit.HOURS))
                    .setDescription(description)
                    .setCategory(categoryUUID.get(rand.nextInt(categoryUUID.size())))
                    .setValue(result2)
                    .setCurrency(UUID.fromString("99f9f3ab-b910-4444-84f6-35f4844767e6"))
                    .setUuidAccount(UUID.fromString("f868a717-5ea6-435e-8703-6b2d87f5394c"))
                    .build();
            operationService.save(operation2);
        }
        return null;
    }

    @RequestMapping(
            value = {"tofrom", "tofrom/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<Operation> index(@RequestParam(name = "to") String toRaw,
                                 @RequestParam(name = "from") String fromRaw,
                                 @RequestParam(name = "uuid") UUID uuidAccount) {
        //TODO ошибки
        LocalDateTime to = conversionService.convert(Long.parseLong(toRaw), LocalDateTime.class);
        LocalDateTime from = conversionService.convert(Long.parseLong(fromRaw), LocalDateTime.class);
        return operationService.getBetweenDates(to, from, uuidAccount);
    }

    @RequestMapping(
            value = {"/privet", "/privet/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public BalanceEntity index(@RequestParam(name = "uuid") UUID uuid) {
        return balanceStorage.getById(uuid);
    }
}