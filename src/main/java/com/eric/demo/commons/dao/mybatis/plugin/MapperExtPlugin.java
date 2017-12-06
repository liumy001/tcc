package com.eric.demo.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class MapperExtPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        //创建Select查询
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "logicDeleteById"));
        update.addAttribute(new Attribute("parameterType", introspectedTable.getColumn("id").getFullyQualifiedJavaType().getFullyQualifiedName()));
        update.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " \n \tset is_del=1 \n \t\twhere id=#{id,jdbcType=" + introspectedTable.getColumn("id").getJdbcTypeName() + "}"));
        document.getRootElement().addElement(update);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
