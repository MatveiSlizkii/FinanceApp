package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.dao.api.ITestStorage;
import by.it_academy.jd2.hw.example.messenger.dao.entities.TestEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.IReportService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/report/test")
public class RestTestController {

    private final IReportService reportService;

    private final ITestStorage testStorage;

    public RestTestController(IReportService reportService,
                              ITestStorage testStorage) {
        this.reportService = reportService;
        this.testStorage = testStorage;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public String create(@RequestBody Map<String, Object> params){
        TestEntity entity = new TestEntity();
        entity.setUuid(UUID.randomUUID());
        System.out.println(params.toString());
        entity.setJson(params.toString());

        testStorage.save(entity);

        return "dfdfd";
    }


}
