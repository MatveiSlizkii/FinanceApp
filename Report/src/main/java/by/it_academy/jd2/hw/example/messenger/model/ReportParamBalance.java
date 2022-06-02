package by.it_academy.jd2.hw.example.messenger.model;

import java.util.*;

public class ReportParamBalance {
    private List<UUID> accounts;

    private ReportParamBalance() {
    }

    public ReportParamBalance(List<UUID> accounts) {
        this.accounts = accounts;
    }

    public ReportParamBalance(Map<String, Objects> params) {
        String obj = params.get("accounts").toString();
        List<String> list = Arrays.asList(obj.substring(1, obj.length()-1).split(", "));
        list.forEach((o)-> this.accounts.add(UUID.fromString(o)));
    }

}
