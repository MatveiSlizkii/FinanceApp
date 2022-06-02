package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class RestAccountController {
    private final ConversionService conversionService;

    private final IAccountService accountService;

    public RestAccountController(ConversionService conversionService, IAccountService accountService) {
        this.conversionService = conversionService;
        this.accountService = accountService;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Page<Account> index(@RequestParam(name = "page") int page,
                               @RequestParam(name = "size") int size) {
        //TODO пейджа и сайза
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return accountService.getAll(pageable);
    }

    @RequestMapping(
            value = {"{uuid}", "{uuid}/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Account index(@PathVariable(name = "uuid") UUID uuid) {
        //TODO ошибки есть ли этот уид

        return accountService.get(uuid);
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Account create(@RequestBody Account account) {
//TODO проверка есть ли все поля в аккаунте
        return accountService.save(account);
    }


    @RequestMapping(
            value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Account update(@RequestBody Account account,
                          @PathVariable UUID uuid,
                          @PathVariable Long dt_update) {
        //TODO проверить все ли данные валидны в аккаунте
        //TODO проверить есть ли такой уид
        //TODO проверить парсится ли дт апдейт

        return accountService.update(uuid, account, dt_update);
    }
}
