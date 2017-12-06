package com.eric.demo.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class MapperEntityExtPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        System.out.println("参数合法有效");
        return true;
    }

    /**
     * 生成dao
     */
    /**
     * 生成实体
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("AbstractAuditingEntity");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.eric.demo.commons.domain.AbstractAuditingEntity");
        topLevelClass.addImportedType(imp);
        topLevelClass.setSuperClass(fqjt);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

}
