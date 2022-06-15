package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.Currency;
import by.it_academy.jd2.hw.example.messenger.model.OperationCategory;
import by.it_academy.jd2.hw.example.messenger.services.api.ICurrencyService;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/classifier/close")
public class RestCloseOperation {

    private final IOperationCategoryService operationCategoryService;
    private final ICurrencyService currencyService;

    public RestCloseOperation(IOperationCategoryService operationCategoryService,
                              ICurrencyService currencyService) {
        this.operationCategoryService = operationCategoryService;
        this.currencyService = currencyService;
    }

    @RequestMapping(
            value = {"/currency", "/currency/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<Currency> index() {
        return currencyService.getAllList();
    }

    @RequestMapping(
            value = {"/operation", "/operation/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<OperationCategory> index1() {
        return operationCategoryService.getAllList();
    }

    @RequestMapping(
            value = {"/currency/{uuid}", "/currency/{uuid}/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Currency index2(@PathVariable (name = "uuid") UUID uuid) {
        return currencyService.get(uuid);
    }


}
