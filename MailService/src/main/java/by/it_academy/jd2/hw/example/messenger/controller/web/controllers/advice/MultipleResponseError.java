package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.advice;


import java.util.List;

public class MultipleResponseError {
    private String logref;
    private List<ValidationError> errors;

    public MultipleResponseError(String logref, List<ValidationError> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
