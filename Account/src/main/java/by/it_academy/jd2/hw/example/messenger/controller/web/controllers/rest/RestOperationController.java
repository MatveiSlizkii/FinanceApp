package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/api/account/{uuid}/operation")
public class RestOperationController {

    private final IOperationService operationService;
    private final ConversionService conversionService;


    public RestOperationController(IOperationService operationService, ConversionService conversionService) {
        this.operationService = operationService;
        this.conversionService = conversionService;
    }


    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Operation> index(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
                                 @PathVariable(name = "uuid") UUID uuidAccount) {
        //TODO есть ли такой уид в базе

        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return operationService.getByUuidAccount(uuidAccount, pageable);
    }


    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Operation create(@RequestBody Operation operation,
                            @PathVariable(name = "uuid") UUID uuidAccount) {
        //TODO на валидность операции
        //TODO есть ли такой уид счета
        operation.setUuidAccount(uuidAccount);
        return operationService.save(operation);
    }


    @PutMapping(value = {"{uuid_operation}/dt_update/{dt_update}",
                        "{uuid_operation}/dt_update/{dt_update}/"},
                        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Operation update(@RequestBody Operation operation,
                            @PathVariable(name = "uuid") UUID uuidAccount,
                            @PathVariable(name = "uuid_operation") UUID uuid,
                            @PathVariable(name = "dt_update") Long dt_update) {
        //TODO сделать проверку принадлежит ли данная операция счету
        //TODO парсится ли лонг
        return operationService.update(uuid, operation, dt_update);
    }



    @DeleteMapping(value = {"{uuid_operation}/dt_update/{dt_update}",
                            "{uuid_operation}/dt_update/{dt_update}/"},
                            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Operation delete(@PathVariable(name = "uuid") UUID uuidAccount,
                            @PathVariable(name = "uuid_operation") UUID uuid,
                            @PathVariable(name = "dt_update") String dtUpdateString) {
        //TODO сделать проверку принадлежит ли данная операция счету
        long dtUpdateLong;
        try {
            dtUpdateLong = Long.parseLong(dtUpdateString);
        } catch (NumberFormatException e) {
            throw new ValidationException("Передан неверный формат параметра последнего обновления");
        }

        Operation operation = operationService.get(uuid);
        operationService.delete(uuid, dtUpdateLong);
        return operation;
    }
}
