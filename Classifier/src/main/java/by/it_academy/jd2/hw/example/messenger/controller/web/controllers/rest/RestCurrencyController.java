package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import by.it_academy.jd2.hw.example.messenger.services.api.ICurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/classifier/currency")
public class RestCurrencyController {

    private final ICurrencyService currencyService;

    public RestCurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Page<Currency> index(@RequestParam(name = "page") int page,
                                @RequestParam(name = "size") int size){
        //TODO возможно дать ошибку на то что пейдж превышает показатель
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        return currencyService.getAll(pageable);
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Currency create(@RequestBody Currency currency) {
        //TODO валидность курренси
        //TODO уникальное название курренси
        return currencyService.save(currency);
    }
}
