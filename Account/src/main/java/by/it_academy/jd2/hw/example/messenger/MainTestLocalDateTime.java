package by.it_academy.jd2.hw.example.messenger;

import by.it_academy.jd2.hw.example.messenger.model.api.TypeAccount;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class MainTestLocalDateTime {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        Long longLocal = localDateTime.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(localDateTime)).toEpochMilli();
        Long my = 1621487764L;
        System.out.println(longLocal + "    00000");
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(my),
                ZoneId.systemDefault());
        System.out.println(localDateTime1 + "hsfkbdfk");

        TypeAccount typeAccount = TypeAccount.CASH;
        System.out.println(typeAccount);
        String string = typeAccount.name();
        System.out.println(string);
        String string2 = "CASH";
        TypeAccount typeAccount1 = TypeAccount.valueOf(string2);
        System.out.println(typeAccount1);
        TypeAccount typeAccount2 = TypeAccount.CASH;
        System.out.println(typeAccount2.equals(TypeAccount.CASH));
        System.out.println("Человеческое время");
        LocalDateTime localDateTime2 = LocalDateTime.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d.MM.uuuu");
        System.out.println(localDateTime2.toLocalDate().format(formatters));
        System.out.println("Вокруг пустота");

    }
}
