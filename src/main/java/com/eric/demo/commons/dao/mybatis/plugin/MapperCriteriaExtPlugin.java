package com.eric.demo.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class MapperCriteriaExtPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("AbstractCriteria");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.eric.demo.commons.domain.AbstractCriteria");
        topLevelClass.addImportedType(imp);
        topLevelClass.setSuperClass(fqjt);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }
}
