package by.it_academy.jd2.hw.example.messenger.errors;

public class ResponseError {
    private String logref;
    private Object message;

    public ResponseError(String logref, Object message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public Object getMessage() {
        return message;
    }
}
