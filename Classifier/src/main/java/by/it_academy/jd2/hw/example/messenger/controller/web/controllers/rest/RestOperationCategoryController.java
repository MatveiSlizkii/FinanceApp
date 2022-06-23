package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationCategoryService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/api/classifier/operation/category")
public class RestOperationCategoryController {

    private IOperationCategoryService operationCategoryService;

    public RestOperationCategoryController(IOperationCategoryService operationCategoryService) {
        this.operationCategoryService = operationCategoryService;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Page<OperationCategory> index(@RequestParam(name = "page") @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                         @RequestParam(name = "size") @Min(value = 1, message = MessageError.PAGE_SIZE)   int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        return operationCategoryService.getAll(pageable);
    }

    @PostMapping(value = {"", "/"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public OperationCategory create(@RequestBody OperationCategory operationCategory) {
        return operationCategoryService.save(operationCategory);
    }
}
