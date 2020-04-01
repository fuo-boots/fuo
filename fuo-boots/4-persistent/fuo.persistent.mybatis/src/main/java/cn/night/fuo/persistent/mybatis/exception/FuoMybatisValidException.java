package cn.night.fuo.persistent.mybatis.exception;

import cn.night.fuo.exception.runtime.FuoRuntimeException;

public class FuoMybatisValidException extends FuoRuntimeException {
    public FuoMybatisValidException(String msg) {
        super(msg);
    }

    public FuoMybatisValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
