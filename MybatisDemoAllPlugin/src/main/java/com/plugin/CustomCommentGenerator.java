package com.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义生成注释的类
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private boolean addRemarkComments;
    private SimpleDateFormat dateFormat;

    public CustomCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = true;
    }

    /**
     * 保存配置项
     * @param properties 配置项
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }

        super.addConfigurationProperties(properties);
    }

    /**
     * 添加javadoc的封装方法
     * @param javaElement java元素
     * @param markAsDoNotDelete 是否显示“do_not_delete_during_merge”
     */
    @Override
    protected void addJavadocTag(JavaElement javaElement,
                                   boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * Dao的类注释
     * @param innerClass
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        addJavadocTag(innerClass, false);
        innerClass.addJavaDocLine(" */");
    }

    /**
     * Dao的类注释
     * @param innerClass
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        addJavadocTag(innerClass, markAsDoNotDelete);
        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * Dao的方法的注释
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/** ");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    /**
     * 添加模型的字段的注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/** "); //$NON-NLS-1$

        String remarks = introspectedColumn.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);  //$NON-NLS-1$
            }
        }

        addJavadocTag(field, false);
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * 添加模型类注释
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        topLevelClass.addJavaDocLine("/** "); //$NON-NLS-1$

        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * Database Table Remarks:");
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" *   " + remarkLine);  //$NON-NLS-1$
            }
        }
//        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$
//        topLevelClass
//                .addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$
//
        sb.append(" * 对应的表是："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());

        addJavadocTag(topLevelClass, true);

        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}
