package com.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class GenericDaoPlugin extends PluginAdapter {

    private String genericDaoClass;

    @Override
    public boolean validate(List<String> warnings) {
        genericDaoClass = properties.getProperty("genericDaoClass");
        return stringHasValue(genericDaoClass);
    }

    // 生成Dao文件前的回调，添加父接口
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        String modelType = introspectedTable.getBaseRecordType();
        String exampleType = introspectedTable.getExampleType();
        List<IntrospectedColumn> keyColumn = introspectedTable.getPrimaryKeyColumns();
        String pkType = "";
        if (keyColumn.size() > 1) {
            pkType = introspectedTable.getPrimaryKeyType();
        } else if (keyColumn.size() == 1) {
            pkType = keyColumn.get(0).getFullyQualifiedJavaType().getShortName();
        }

        // 生成Dao的父接口
        String genericDaoShortName = new FullyQualifiedJavaType(genericDaoClass).getShortName();
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(genericDaoShortName + "<" + modelType  + ", " + exampleType + "," + pkType + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(genericDaoClass);
        interfaze.addSuperInterface(fqjt);  // 添加 extends BaseDao<user>
        interfaze.addImportedType(imp);     // 添加import common.BaseDao;

        // dao不生成方法
        interfaze.getMethods().clear();

        return true;
    }

}
