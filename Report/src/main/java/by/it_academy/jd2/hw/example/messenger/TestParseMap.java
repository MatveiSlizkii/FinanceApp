package by.it_academy.jd2.hw.example.messenger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestParseMap {
    public static void main(String[] args) {

        Map<String, String> mainMapString = new HashMap<>();

        mainMapString.put("uuid", UUID.randomUUID().toString());
        mainMapString.put("int", "456");
        mainMapString.put("accounts", "[ab4fd3b1-b951-4c26-b373-1f5cab202f00, " +
                "766b6870-12d7-4189-a42f-b98776519016, " +
                "f868a717-5ea6-435e-8703-6b2d87f5394c" +
                "]");
        System.out.println(mainMapString.get("accounts"));
        System.out.println(LocalDate.now());


    }
}
