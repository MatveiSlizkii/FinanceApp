package by.it_academy.jd2.hw.example.messenger.errors;

import java.util.ArrayList;
import java.util.List;

public class ArgumentException extends IllegalArgumentException{

    private List<ArgumentError> argumentErrors = new ArrayList<>();
    private ArgumentError argumentError;

    public ArgumentException() {
    }

    public ArgumentException(String s, ArgumentError argumentError) {
        super(s);
        this.argumentErrors.add(argumentError);
    }

    public ArgumentException(String s, List<ArgumentError> argumentErrors) {
        super(s);
        this.argumentErrors = argumentErrors;
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

    private void addError(ArgumentError argumentError){
       this.argumentErrors.add(argumentError);
    }

    public List<ArgumentError> getArgumentErrors() {
        return argumentErrors;
    }

    public ArgumentError getArgumentError() {
        return argumentError;
    }

    @Override
    public String toString() {
        return "ArgumentException{" +
                "argumentErrors=" + argumentErrors +
                '}';
    }
}
