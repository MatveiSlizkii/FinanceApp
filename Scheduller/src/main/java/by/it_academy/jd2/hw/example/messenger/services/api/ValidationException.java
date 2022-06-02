package by.it_academy.jd2.hw.example.messenger.services.api;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends IllegalArgumentException{

    private List<ValidationError> errors = new ArrayList<>();

    public ValidationException(String s, List<ValidationError> errors) {
        super(s);
        this.errors = errors;
    }

    public ValidationException() {
    }

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public void add(ValidationError e){
        this.errors.add(e);
    }
}
