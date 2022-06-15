package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.entity.CurrencyEntity;
import by.it_academy.jd2.hw.example.messenger.model.Currency;
import by.it_academy.jd2.hw.example.messenger.services.api.ICurrencyService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/api/classifier/currency")
public class RestCurrencyController {

    private final ICurrencyService currencyService;

    public RestCurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = {"", "/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Currency> index(@RequestParam(name = "page") @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                @RequestParam(name = "size") @Min(value = 1, message = MessageError.PAGE_SIZE) int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        return currencyService.getAll(pageable);
    }

    @PostMapping(value = {"", "/"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Currency create(@RequestBody Currency currency) {
        //TODO валидность курренси
        //TODO уникальное название курренси
        return currencyService.save(currency);
    }
}
