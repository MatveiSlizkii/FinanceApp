package by.it_academy.jd2.hw.example.messenger.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ResponseError> securityHandler(SecurityException e) {
        return new ResponseEntity<>(new ResponseError("errorSecurity", "Ошибка безопасности"), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ArgumentException.class)
    public ResponseEntity<ResponseError> securityHandler(ArgumentException e) {
        return new ResponseEntity<>(new ResponseError("Неверно введены поля", e.getArgumentErrors()), HttpStatus.BAD_REQUEST);
    }
}
