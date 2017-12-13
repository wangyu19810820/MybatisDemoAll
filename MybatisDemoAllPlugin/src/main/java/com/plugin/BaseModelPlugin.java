package com.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 生成模型类的插件
 * 添加父类声明，不生成父类中已经存在的业务字段和方法，仍然生成Object定义的方法
 */
public class BaseModelPlugin extends PluginAdapter {

    /** 模型父类的完整类名 */
    private String baseModelClass;

    /** Object类的方法,这些方法会被生成 */
    private List<String> objectMethodNameList = Arrays.asList("toString", "hashCode", "equals");

    @Override
    public boolean validate(List<String> warnings) {
        baseModelClass = properties.getProperty("baseModelClass");
        return stringHasValue(baseModelClass);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType baseClass = new FullyQualifiedJavaType(baseModelClass);
        topLevelClass.setSuperClass(baseClass);
        topLevelClass.addImportedType(baseClass);

        List<String> baseModelFieldNameList = new ArrayList<>();
        List<String> baseModelMethodNameList = new ArrayList<>();
        try {
            Class class1 = Class.forName(baseModelClass);
            java.lang.reflect.Field[] fieldArray = class1.getDeclaredFields();
            java.lang.reflect.Field.setAccessible(fieldArray, true);
            baseModelFieldNameList = Arrays.stream(fieldArray)
                                            .map(java.lang.reflect.Field::getName)
                                            .collect(Collectors.toList());
            baseModelMethodNameList = Arrays.stream(class1.getMethods())
                                            .map(java.lang.reflect.Method::getName)
                                            .collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        List<Field> fields = topLevelClass.getFields();
        Iterator<Field> fieldIterator = fields.iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            if (baseModelFieldNameList.contains(field.getName())) {
                fieldIterator.remove();
            }
        }

        List<Method> methodList = topLevelClass.getMethods();
        Iterator<Method> methodIterator = methodList.iterator();
        while (methodIterator.hasNext()) {
            Method method = methodIterator.next();

            if (baseModelMethodNameList.contains(method.getName())
                    && !objectMethodNameList.contains(method.getName())) {
                methodIterator.remove();
            }
        }

        return true;
    }

}
