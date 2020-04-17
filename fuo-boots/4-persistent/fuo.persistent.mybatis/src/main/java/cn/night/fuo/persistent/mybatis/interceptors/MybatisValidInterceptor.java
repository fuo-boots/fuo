//package cn.night.fuo.persistent.mybatis.interceptors;
//
//import cn.night.fuo.persistent.mybatis.MybatisProperties;
//import cn.night.fuo.persistent.mybatis.exception.FuoMybatisValidException;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.select.Join;
//import net.sf.jsqlparser.statement.select.PlainSelect;
//import net.sf.jsqlparser.statement.select.Select;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.tuple.Pair;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.util.List;
//import java.util.Properties;
//
//@Intercepts({
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
//})
//@Slf4j
//public class MybatisValidInterceptor extends FuoMybatisBaseInterceptor {
//
//    private final MybatisProperties properties;
//
//    public MybatisValidInterceptor(MybatisProperties properties) {
//        this.properties = properties;
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Pair<String, String> pair = this.getStatement(invocation);
//        if (pair == null || StringUtils.isBlank(pair.getValue())) {
//            log.error("Mybatis join check execution has no statement");
//            throw new FuoMybatisValidException("Mybatis join check has no statement");
//        }
//        Statement statement = CCJSqlParserUtil.parse(pair.getValue());
//
//        //没有查询体的查询,即无条件查询
//        if(statement instanceof Select && ((Select) statement).getSelectBody() instanceof PlainSelect)
//        {
//            List<Join> joins = ((PlainSelect) ((Select) statement).getSelectBody()).getJoins();
//            int count = properties.getJoinLimit() == null ? 0 : properties.getJoinLimit();
//            if (joins != null && joins.size() > count) {
//                log.error("Select statement contains join expression, sql: {}", pair.getValue());
//                throw new FuoMybatisValidException("Select statement contains join expression");
//            }
//            if (((PlainSelect) ((Select) statement).getSelectBody()).getWhere() == null) {
//                log.error("Select statement didnt have conditions expression, sql: {}", pair.getValue());
//                throw new FuoMybatisValidException("Select statement didnt have conditions expression");
//            }
//        }
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return super.plugin(target);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        super.setProperties(properties);
//    }
//}
