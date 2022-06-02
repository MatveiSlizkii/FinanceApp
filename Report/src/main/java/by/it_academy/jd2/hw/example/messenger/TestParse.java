package by.it_academy.jd2.hw.example.messenger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestParse {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("d938418c-58ff-4376-897c-1b904d1dc2ed");
        stringList.add("7670f360-ae8e-467c-a1c9-12f3486b0f9c");
        Object obj = stringList.toString();
        String strObj = obj.toString();
        System.out.println(strObj);
        List<String> list = Arrays.asList(strObj.substring(1, strObj.length()-1).split(", "));
        List<UUID> uuids = new ArrayList<>();
        list.forEach((o)-> uuids.add(UUID.fromString(o)));
        System.out.println();
        System.out.println(uuids);

        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")));

        LocalDateTime ldt = LocalDateTime.now();
        String date = ldt.toLocalDate().format(DateTimeFormatter.ofPattern("d.MM.uuuu"));
        System.out.println(date);

    }
}
