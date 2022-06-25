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
        stringList.add("sdsds");
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("d938418c-58ff-4376-897c-1b904d1dc2ed");
        stringList2.add("7670f360-ae8e-467c-a1c9-12f3486b0f9c");

        stringList2.removeAll(stringList);
        System.out.println(stringList2.size());
        System.out.println(stringList2);

    }
}
