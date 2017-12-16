package com.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * Dao由XxxxMapper改名为XxxxDao
 */
public class RenameDaoPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getMyBatis3JavaMapperType();
        if (oldType.toLowerCase().endsWith("dao")) {
            return;
        }
        if (oldType.endsWith("Mapper")) {
            oldType = oldType.substring(0, oldType.length() - 6) + "Dao";
            introspectedTable.setMyBatis3JavaMapperType(oldType);
        } else {
            introspectedTable.setMyBatis3JavaMapperType(oldType + "Dao");
        }
    }
}
