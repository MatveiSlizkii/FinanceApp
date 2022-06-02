package by.it_academy.jd2.hw.example.messenger.errors;

import java.util.ArrayList;
import java.util.List;

public class ArgumentException extends IllegalArgumentException{

    private List<ArgumentError2> argumentError2 = new ArrayList<>();

    public ArgumentException() {
    }

    public ArgumentException(String s, List<ArgumentError2> argumentError2) {
        super(s);
        this.argumentError2 = argumentError2;
    }

    public ArgumentException(String s) {
        super(s);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }

    public List<ArgumentError2> getArgumentError2s() {
        return argumentError2;
    }

    private void addError(ArgumentError2 argumentErrors2){
        this.argumentError2.add(argumentErrors2);
    }

    public List<ArgumentError2> getArgumentErrors() {
        return argumentError2;
    }

    @Override
    public String toString() {
        return "ArgumentException{" +
                "argumentErrors=" + argumentError2 +
                '}';
    }
}
