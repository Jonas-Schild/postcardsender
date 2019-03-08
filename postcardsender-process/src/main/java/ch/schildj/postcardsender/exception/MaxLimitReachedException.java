package ch.schildj.postcardsender.exception;

/* Exception when User reach limit of allowed card */
public class MaxLimitReachedException extends Exception {
    public MaxLimitReachedException(String errorMessage) {
        super(errorMessage);
    }
}