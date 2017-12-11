package com.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class GeneratorPlugin extends PluginAdapter {

    private List<String> baseModelFieldNameList = Arrays.asList("status", "addDttm", "addUser", "lastUpdDttm", "lastUpdUser", "remark");
    private String baseModelClass;

    @Override
    public boolean validate(List<String> warnings) {
        baseModelClass = properties.getProperty("baseModelClass");
        return stringHasValue(baseModelClass);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
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
//            if (baseModelFieldNameList.contains(field.getName())) {
//                methodIterator.remove();
//            }
        }

        return true;
    }
}
