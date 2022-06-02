package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import by.it_academy.jd2.hw.example.messenger.services.api.IValidateArgument;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Page<Operation> index(@RequestParam(name = "page") int page,
                                 @RequestParam(name = "size") int size,
                                 @PathVariable(name = "uuid") UUID uuidAccount) {
        //TODO есть ли такой уид в базе

        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return operationService.getByUuidAccount(uuidAccount, pageable);
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Operation create(@RequestBody Operation operation,
                            @PathVariable(name = "uuid") UUID uuidAccount) {
        //TODO на валидность операции
        //TODO есть ли такой уид счета
//        IValidateArgument validateArgument = new ValidateOperation();
//        validateArgument.validate(operation);
        operation.setUuidAccount(uuidAccount);
        return operationService.save(operation);
    }

    @RequestMapping(
            value = {"{uuid_operation}/dt_update/{dt_update}", "{uuid_operation}/dt_update/{dt_update}/"},
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Operation update(@RequestBody Operation operation,
                            @PathVariable(name = "uuid") UUID uuidAccount,
                            @PathVariable(name = "uuid_operation") UUID uuid,
                            @PathVariable(name = "dt_update") Long dt_update) {
        //TODO сделать проверку принадлежит ли данная операция счету
        //TODO парсится ли лонг
        return operationService.update(uuid, operation, dt_update);
    }


    @RequestMapping(
            value = {"{uuid_operation}/dt_update/{dt_update}", "{uuid_operation}/dt_update/{dt_update}/"},
            method = RequestMethod.DELETE,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Operation delete(@PathVariable(name = "uuid") UUID uuidAccount,
                            @PathVariable(name = "uuid_operation") UUID uuid,
                            @PathVariable(name = "dt_update") Long dt_update) {
        //TODO сделать проверку принадлежит ли данная операция счету
        //TODO парсится ли лонг
        Operation operation = operationService.get(uuid);
        operationService.delete(uuid, dt_update);
        return operation;
    }
}
