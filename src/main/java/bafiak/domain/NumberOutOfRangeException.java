package bafiak.domain;

public class NumberOutOfRangeException extends RuntimeException {
    public NumberOutOfRangeException(String s) {
        super(s);
    }
}
