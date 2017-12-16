package com.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 为Dao添加基类
 */
public class GenericDaoPlugin extends PluginAdapter {

    private String genericDaoClass;
    private String serviceTargetDir;
    private String serviceTargetPackage;
    private ShellCallback shellCallback = null;

    public GenericDaoPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public boolean validate(List<String> warnings) {
        genericDaoClass = properties.getProperty("genericDaoClass");
        serviceTargetDir = properties.getProperty("targetProject");
        serviceTargetPackage = properties.getProperty("targetPackage");
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

        try {
            File mapperDir = shellCallback.getDirectory(serviceTargetDir, serviceTargetPackage);
            String daoName = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName();
            File mapperServiceFile = new File(mapperDir, daoName + ".java");

            System.out.println(mapperServiceFile);

            // 文件不存在
            if (!mapperServiceFile.exists()) {
                return true;
            } else {
                return false;
            }

        } catch (ShellException e) {
            e.printStackTrace();
        }

        return true;
    }

}
