package cn.night.fuo.persistent.mybatis.interceptors;

import cn.night.fuo.persistent.mybatis.FuoMybatisInterceptor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class FuoMybatisBaseInterceptor implements FuoMybatisInterceptor {
    protected Pair<String, String> getStatement(Invocation invocation) {
        Object[] args = invocation.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Pair<Pageable, Object> pair = this.getStatementParam(args[1]);
        BoundSql boundSql = mappedStatement.getBoundSql(pair.getValue());
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        return Pair.of(mappedStatement.getId(), sql);
    }

    protected Pair<Pageable, Object> getStatementParam(Object parameter) {
        Pageable pageable = null;
        if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            int duplicate = 0;
            for (Map.Entry<String, Object> entry : ((MapperMethod.ParamMap<Object>) parameter).entrySet()) {
                if (entry.getValue() instanceof Pageable) {
                    pageable = (Pageable) entry.getValue();
                    continue;
                }
                if (param.containsValue(entry.getValue()) && !param.containsKey(entry.getKey())) {
                    duplicate++;
                }
                if (!param.containsKey(entry.getKey())) {
                    param.put(entry.getKey(), entry.getValue());
                }
            }
            Object entity = param.entrySet().iterator().next().getValue();
            Object result = pageable != null && param.size() - duplicate == 1 && entity instanceof CommonEntity ? entity : param;
            return Pair.of(pageable, result);
        }
        return Pair.of(null, parameter);
    }

    protected Pair<String, String> getStatementWithParam(Invocation invocation) {
        Object[] args = invocation.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Configuration configuration = mappedStatement.getConfiguration();

        BoundSql boundSql = args.length >= 6 && args[5] instanceof BoundSql ? (BoundSql) args[5] : mappedStatement.getBoundSql(args.length >= 2 ? args[1] : null);
        if (boundSql == null) {
            return null;
        }
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();

        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterObject != null && !CollectionUtils.isEmpty(parameterMappings)) {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (metaObject.getValue(propertyName) != null) {
                    sql = sql.replaceFirst("\\?", metaObject.getValue(propertyName).toString());
                }
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    sql = sql.replaceFirst("\\?", boundSql.getAdditionalParameter(propertyName).toString());
                }
            }
        }
        return Pair.of(mappedStatement.getId(), sql);
    }
}
