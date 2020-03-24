package cn.night.fuo.core.env;

import cn.night.fuo.exception.check.FuoEnvironmentInitializeFailedException;

public interface FuoEnvironmentBuilder {
    void doBuild() throws FuoEnvironmentInitializeFailedException;

    default void build() throws FuoEnvironmentInitializeFailedException {
        try {
            this.doBuild();
        } catch (Exception e) {
            throw new FuoEnvironmentInitializeFailedException(e.getMessage(), e);
        }
    }
}
