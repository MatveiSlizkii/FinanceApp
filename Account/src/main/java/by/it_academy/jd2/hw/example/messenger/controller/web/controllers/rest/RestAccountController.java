package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
import by.it_academy.jd2.hw.example.messenger.services.api.IAccountService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
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


    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Account> index(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                               @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return accountService.getAll(pageable);
    }

    @GetMapping(value = {"{uuid}", "{uuid}/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account index(@PathVariable(name = "uuid") UUID uuid) {

        return accountService.get(uuid);
    }


    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        return accountService.save(account);
    }



    @PutMapping(value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account update(@RequestBody Account account,
                          @PathVariable UUID uuid,
                          @PathVariable String dt_update) {
        try {
            Long dtUpdateLong = Long.parseLong(dt_update);
            conversionService.convert(dtUpdateLong, LocalDateTime.class);
        } catch (NumberFormatException e){
            throw new ValidationException("Переданы данная версия не соответствует формату");
        }

        return accountService.update(uuid, account, Long.parseLong(dt_update));
    }
}
