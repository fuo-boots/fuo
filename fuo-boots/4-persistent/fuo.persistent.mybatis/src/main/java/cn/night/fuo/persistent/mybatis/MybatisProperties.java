package cn.night.fuo.persistent.mybatis;

import cn.night.fuo.core.conf.IConf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "fuo.database.mybatis")
public class MybatisProperties implements IConf, InitializingBean {

//    @NotNull
    private Boolean enable;

//    @Min(0)
    private Integer joinLimit;

//    @NotEmpty
    private String mapperLocation;

//    @NotNull
    private Map<String, ConnectionGroup> connectionGroup;

    @Data
    public static class ConnectionGroup {

//        @NotNull
        private Boolean checkDefault;

//        @NotNull
        @NestedConfigurationProperty
        private ConnectionConfig masterConnection;

        @NestedConfigurationProperty
        private List<ConnectionConfig> slaveConnection;
    }

    @Data
    public static class ConnectionConfig {

//        @NotNull
        private ConnectionType type;

//        @NotEmpty
        private String url;

//        @NotEmpty
        private String userName;

//        @NotEmpty
        private String password;

//        @NotNull
//        @Min(10)
        private Integer minConnection;

//        @NotNull
//        @Min(10)
        private Integer maxConnection;

//        @Min(1000)
        private Integer idleTimeout;

//        @Min(5000)
        private Integer connectionTimeout;
    }

    @Getter
    @AllArgsConstructor
    public enum ConnectionType {

        MYSQL("com.mysql.cj.jdbc.Driver"),

        ORACLE("oracle.jdbc.driver.OracleDriver"),

        CDS(""),

        ;

        private String driverClassName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
