package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest.advice;

public class SingleResponseError {
    private String logref;
    private String message;

    public SingleResponseError(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }
}
