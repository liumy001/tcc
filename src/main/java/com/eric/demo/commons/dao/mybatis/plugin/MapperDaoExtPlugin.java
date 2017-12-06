package com.eric.demo.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapperDaoExtPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        System.out.println("参数合法有效");
        return true;
    }

    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseDao<" + introspectedTable.getBaseRecordType() + "," + introspectedTable.getExampleType() + "," + introspectedTable.getColumn("id").getFullyQualifiedJavaType().getFullyQualifiedName() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.eric.demo.commons.dao.BaseDao");
        interfaze.addSuperInterface(fqjt);// 添加 extends BaseDao<User>
        interfaze.addImportedType(imp);// 添加import common.BaseDao;
        interfaze.getMethods().clear();
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * @数表名称 " + introspectedTable.getFullyQualifiedTable());
        interfaze.addJavaDocLine(" * @开发日期 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        interfaze.addJavaDocLine(" * @开发作者 by:liumy ");
        interfaze.addJavaDocLine(" */");
        return true;
    }

}
