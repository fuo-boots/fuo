package cn.night.fuo.core.exception.runtime;

public class FuoRuntimeException extends RuntimeException {
    public FuoRuntimeException(String msg) {
        super(msg);
    }

    public FuoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
