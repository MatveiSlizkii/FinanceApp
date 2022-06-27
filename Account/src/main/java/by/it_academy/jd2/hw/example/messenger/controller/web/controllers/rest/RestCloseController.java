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
@RequestMapping("/account/close")
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
            value = {"findallaccount/{login}", "findallaccount/{login}/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<UUID> index(@PathVariable(name = "login") String login) {
        //TODO ошибки
        List<UUID> uuidsAccounts = accountService.uuidsAccountsByUser(login);
        return uuidsAccounts;
    }



}