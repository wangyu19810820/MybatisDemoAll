package com.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class ServicePlugin extends PluginAdapter {

    // 扩展
    private String serviceTargetDir;
    private String serviceTargetPackage;
    private ShellCallback shellCallback = null;

    public ServicePlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public boolean validate(List<String> warnings) {
        serviceTargetDir = properties.getProperty("targetProject");
        serviceTargetPackage = properties.getProperty("targetPackage");
        boolean valid = stringHasValue(serviceTargetDir);
        return valid;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();

        String modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()).getShortName();
        String exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType()).getShortName();

        List<IntrospectedColumn> keyColumn = introspectedTable.getPrimaryKeyColumns();
        String pkType = "";
        if (keyColumn.size() > 1) {
            pkType = introspectedTable.getPrimaryKeyType();
        } else if (keyColumn.size() == 1) {
            pkType = keyColumn.get(0).getFullyQualifiedJavaType().getShortName();

        } else {
            pkType = "Object";
        }

        String interfaceName = serviceTargetPackage + "." + modelType + "Service";
        Interface serviceInterface = new Interface(new FullyQualifiedJavaType(interfaceName));
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        serviceInterface.addSuperInterface(new FullyQualifiedJavaType("GenericService<" + modelType + "," + exampleType + "," + pkType + ">"));
        serviceInterface.addImportedType(new FullyQualifiedJavaType("com.base.GenericService"));
        serviceInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        serviceInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        serviceInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        GeneratedJavaFile generatedServiceFile = new GeneratedJavaFile(serviceInterface, serviceTargetDir, javaFormatter);

        TopLevelClass otherServiceImpl = new TopLevelClass(new FullyQualifiedJavaType(serviceTargetPackage + "." + modelType + "ServiceImpl"));
        otherServiceImpl.setVisibility(JavaVisibility.PUBLIC);
        otherServiceImpl.setSuperClass("com.base.GenericServiceImpl<" + modelType + "," + exampleType + "," + pkType + ">");
        otherServiceImpl.addSuperInterface(new FullyQualifiedJavaType(interfaceName));
        otherServiceImpl.addImportedType(interfaceName);
        otherServiceImpl.addImportedType("org.springframework.stereotype.Service");
        otherServiceImpl.addAnnotation("@Service");
        otherServiceImpl.addImportedType(new FullyQualifiedJavaType("com.base.GenericServiceImpl"));
        otherServiceImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        otherServiceImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        otherServiceImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        GeneratedJavaFile generatedServiceImplFile = new GeneratedJavaFile(otherServiceImpl, serviceTargetDir, javaFormatter);

        try {
            File mapperDir = shellCallback.getDirectory(serviceTargetDir, serviceTargetPackage);
            File mapperServiceFile = new File(mapperDir, generatedServiceFile.getFileName());
            File mapperServiceImplFile = new File(mapperDir, generatedServiceImplFile.getFileName());

            // 文件不存在
//            if (!mapperServiceFile.exists()) {
            mapperJavaFiles.add(generatedServiceFile);
//            }
//            if (!mapperServiceImplFile.exists()) {
            mapperJavaFiles.add(generatedServiceImplFile);
//            }
        } catch (ShellException e) {
            e.printStackTrace();
        }
        return mapperJavaFiles;
    }

}
