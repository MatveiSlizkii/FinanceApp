package by.it_academy.jd2.hw.example.messenger.errors;

public class ArgumentError2 {
    private String field;
    private String message;

    public ArgumentError2(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "field='" + field + '\'' +
                ", message='" + message;
    }
}
