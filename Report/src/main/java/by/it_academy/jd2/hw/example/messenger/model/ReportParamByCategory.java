package by.it_academy.jd2.hw.example.messenger.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.*;

public class ReportParamByCategory {
    private List<UUID> accounts;
    private LocalDateTime to;
    private LocalDateTime from;
    @Autowired
    private ConversionService conversionService;

    private ReportParamByCategory() {
    }

    public ReportParamByCategory(List<UUID> accounts, LocalDateTime to, LocalDateTime from) {
        this.accounts = accounts;
        this.to = to;
        this.from = from;
    }

    public ReportParamByCategory(Map<String, Objects> params) {
        //аккаунты
        String obj = params.get("accounts").toString();
        List<String> list = Arrays.asList(obj.substring(1, obj.length()-1).split(", "));
        list.forEach((o)-> this.accounts.add(UUID.fromString(o)));
        //to
        String toStr = params.get("to").toString();
        long toLong = Long.parseLong(toStr);
        this.to = conversionService.convert(toLong, LocalDateTime.class);
        //from
        String fromStr = params.get("from").toString();
        long fromLong = Long.parseLong(fromStr);
        this.from = conversionService.convert(fromLong, LocalDateTime.class);
    }


}
