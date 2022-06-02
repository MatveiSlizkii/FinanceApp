//package by.it_academy.jd2.hw.example.messenger.services;
//
//
//import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
//import by.it_academy.jd2.hw.example.messenger.services.api.IValidateArgument;
//
//import java.util.ArrayList;
//
//public class ValidateOperation implements IValidateArgument<Operation> {
//    @Override
//    public void validate(Operation operation) {
//        List<ArgumentError> argumentErrors = new ArrayList<>();
//        if (operation.getDate() == null) {
//            argumentErrors.add(new ArgumentError("Date",
//                    "Вы не ввели дату, в которую была совершена операция"));
//        }
//        if (operation.getDescription().isEmpty()) {
//            argumentErrors.add(new ArgumentError("Description",
//                    "Вы не указали описание к операции"));
//        }
//        if (operation.getCategory() == null) {
//            argumentErrors.add(new ArgumentError("Category",
//                    "Вы не указали категорию операции"));
//        }
//        if (operation.getValue() == 0) {
//            argumentErrors.add(new ArgumentError("Value",
//                    "Вы не указали размер"));
//        }
//        if (operation.getCurrency() == null) {
//            argumentErrors.add(new ArgumentError("Currency",
//                    "Вы не указали валюту"));
//        }
//        if (argumentErrors.size() > 0){
//            throw new ArgumentException("Ошибка создания операции", argumentErrors);
//        }
//    }
//}
