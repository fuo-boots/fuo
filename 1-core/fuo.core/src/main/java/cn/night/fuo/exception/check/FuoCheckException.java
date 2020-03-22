package cn.night.fuo.core.exception.check;

public class FuoCheckException extends Exception {
    public FuoCheckException(String message) {
        super(message);
    }

    public FuoCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
