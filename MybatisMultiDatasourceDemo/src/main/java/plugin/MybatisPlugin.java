package plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import other.HandlerDataSource;

import java.sql.Statement;
import java.util.Properties;

/**
 * java公版公用字段赋值
 */
//@Intercepts(@Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class))
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        Object target = invocation.getTarget();
//        MetaObject metaObject = SystemMetaObject.forObject(target);
//        StatementHandler statementHandler = (StatementHandler)metaObject.getValue("delegate");
//        // 实际StatementHandler类型是预处理或普通，则执行
//        if (statementHandler instanceof PreparedStatementHandler) {
//            // 获取参数对象，用Mybatis的API设置值
//            Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
//            MetaObject metaObject1 = configuration.newMetaObject(metaObject.getValue("parameterHandler.parameterObject"));
//            SqlCommandType sqlCommandType = (SqlCommandType) metaObject.getValue("parameterHandler.mappedStatement.sqlCommandType");
//
//            String userName = "admin";
//
//        }

        boolean syschronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object[] objects = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) objects[0];
        if (syschronizationActive != true) {

        }
        System.out.println("SqlCommandType:" + mappedStatement.getSqlCommandType());
        HandlerDataSource.setDbType(mappedStatement.getSqlCommandType());

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
