package cn.night.fuo.core.exception.check;

public class FuoEnvironmentInitializeFailedException extends FuoCheckException {
    public FuoEnvironmentInitializeFailedException(String msg){
        super(msg);
    }

    public FuoEnvironmentInitializeFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
