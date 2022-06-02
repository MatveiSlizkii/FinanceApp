//package by.it_academy.jd2.hw.example.messenger.services;
//
//import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.errors.ArgumentError;
//import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.errors.ArgumentException;
//import by.it_academy.jd2.hw.example.messenger.model.dto.Account;
//import by.it_academy.jd2.hw.example.messenger.services.api.IValidateArgument;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ValidateAccount implements IValidateArgument<Account> {
//    @Override
//    public void validate(Account account) {
//        List<ArgumentError> argumentErrors = new ArrayList<>();
//
//        if (account.getTitle().isEmpty()) {
//            argumentErrors.add(new ArgumentError("Title",
//                    "Вы не указали название"));
//        }
//        if (account.getDescription().isEmpty()) {
//            argumentErrors.add(new ArgumentError("Description",
//                    "Вы не указали описание"));
//        }
//        if (account.getType() == null) {
//            argumentErrors.add(new ArgumentError("Type",
//                    "Вы не указали тип"));
//        }
//        if (account.getCurrency() == null) {
//            argumentErrors.add(new ArgumentError("Currency",
//                    "Вы не указали тип валюты"));
//        }
//        if (argumentErrors.size() > 0){
//            throw new ArgumentException("Ошибка создания аккаунта", argumentErrors);
//        }
//    }
//}
