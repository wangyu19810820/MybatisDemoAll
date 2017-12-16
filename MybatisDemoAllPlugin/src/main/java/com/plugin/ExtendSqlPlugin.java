package com.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 生成sql的两个xml文件，一个用于自定义函数，一个用于固定函数
 */
public class ExtendSqlPlugin extends PluginAdapter {

    private String mapperFileName;
    private ShellCallback shellCallback = null;

    public ExtendSqlPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public boolean validate(List<String> warnings) {
        boolean valid = true;

        if (!stringHasValue(properties
                .getProperty("targetProject"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetProject")); //$NON-NLS-1$
            valid = false;
        }

        if (!stringHasValue(properties
                .getProperty("targetPackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetPackage")); //$NON-NLS-1$
            valid = false;
        }

        if (!stringHasValue(properties
                .getProperty("targetBasePackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetBasePackage")); //$NON-NLS-1$
            valid = false;
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        mapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        String baseMmlFileName = mapperFileName.substring(0, mapperFileName.length() - 10) + "BaseMapper.xml";
        introspectedTable.setMyBatis3XmlMapperFileName(baseMmlFileName);
        introspectedTable.setMyBatis3XmlMapperPackage(properties.getProperty("targetBasePackage"));
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        List<GeneratedXmlFile> mapperFiles = new ArrayList<>();
        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);

        XmlElement root = new XmlElement("mapper"); //$NON-NLS-1$
        root.addAttribute(new Attribute("namespace", introspectedTable.getMyBatis3SqlMapNamespace()));
        root.addElement(new TextElement(""));
        document.setRootElement(root);

        GeneratedXmlFile gxf = new GeneratedXmlFile(document, properties
                .getProperty("fileName", mapperFileName), //$NON-NLS-1$ //$NON-NLS-2$
                properties.getProperty("targetPackage"), //$NON-NLS-1$
                properties.getProperty("targetProject"), //$NON-NLS-1$
                false, context.getXmlFormatter());
        try {
            File mapperDir = shellCallback.getDirectory(properties.getProperty("targetProject"),
                    properties.getProperty("targetPackage"));
            File mapperFile = new File(mapperDir, mapperFileName);

            // 文件不存在
            if (!mapperFile.exists()) {
                mapperFiles.add(gxf);
            }
        } catch (ShellException e) {
            e.printStackTrace();
        }
        return mapperFiles;
    }

    // UpdateByExample方法去除审计字段
    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> list = element.getElements();
        Iterator<Element> iterator = list.iterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (e instanceof TextElement) {
                TextElement te = (TextElement) e;
                if (te.getContent().contains("add_dttm")
                        || te.getContent().contains("add_user")
                        || te.getContent().contains("last_upd_dttm")
                        || te.getContent().contains("last_upd_user")) {
                    iterator.remove();
                }
            }
        }
        return true;
    }

    // UpdateByExample方法去除审计字段
    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> list = element.getElements();
        Iterator<Element> iterator = list.iterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (e instanceof TextElement) {
                TextElement te = (TextElement) e;
                if (te.getContent().contains("add_dttm")
                        || te.getContent().contains("add_user")
                        || te.getContent().contains("last_upd_dttm")
                        || te.getContent().contains("last_upd_user")) {
                    iterator.remove();
                }
            }
        }
        return true;
    }
}
