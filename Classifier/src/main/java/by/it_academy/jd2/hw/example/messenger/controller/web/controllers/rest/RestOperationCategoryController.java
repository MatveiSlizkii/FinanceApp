package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public Page<OperationCategory> index(@RequestParam(name = "page") int page,
                                         @RequestParam(name = "size") int size){
        //TODO на большой пейдж
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        return operationCategoryService.getAll(pageable);
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public OperationCategory create(@RequestBody OperationCategory operationCategory) {
        //TODO все ли переданы аргументы важные
        //TODO уникальные названия категорий
        return operationCategoryService.save(operationCategory);
    }
}
