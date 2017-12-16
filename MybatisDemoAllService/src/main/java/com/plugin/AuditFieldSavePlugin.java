package com.plugin;

import com.base.BaseModel;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

/**
 * java公版公用字段赋值
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class))
public class AuditFieldSavePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();

        MetaObject metaObject = SystemMetaObject.forObject(target);
        StatementHandler statementHandler = (StatementHandler)metaObject.getValue("delegate");
        // 实际StatementHandler类型是预处理或普通，则执行
        if (statementHandler instanceof PreparedStatementHandler) {
            // 获取参数对象，用Mybatis的API设置值
            Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
            MetaObject metaObject1 = configuration.newMetaObject(metaObject.getValue("parameterHandler.parameterObject"));
            SqlCommandType sqlCommandType = (SqlCommandType) metaObject.getValue("parameterHandler.mappedStatement.sqlCommandType");

            String userName = "admin";
            Subject curUser = SecurityUtils.getSubject();
            if (curUser.isAuthenticated()) {
                userName = (String)curUser.getPrincipal();
            }
            if (metaObject1.getOriginalObject() instanceof BaseModel) {
                if (sqlCommandType == SqlCommandType.INSERT) {
                    metaObject1.setValue("addDttm", new Date());
                    metaObject1.setValue("addUser", userName);
                    metaObject1.setValue("lastUpdDttm", new Date());
                    metaObject1.setValue("lastUpdUser", userName);
                } else if (sqlCommandType == SqlCommandType.UPDATE) {
                    metaObject1.setValue("lastUpdDttm", new Date());
                    metaObject1.setValue("lastUpdUser", userName);
                }
            }
        }
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
