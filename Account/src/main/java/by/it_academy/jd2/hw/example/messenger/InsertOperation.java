package by.it_academy.jd2.hw.example.messenger;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class InsertOperation {
    public static void main(String[] args) {

        double value = Math.random()*100 - 50;
        double scale = Math.pow(10, 2);
        double result = Math.ceil(value * scale) / scale;
        System.out.println(result);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate1 = localDateTime.toLocalDate();

        System.out.println(localDate1);



    }
}
