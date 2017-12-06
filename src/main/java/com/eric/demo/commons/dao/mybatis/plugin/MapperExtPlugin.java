package com.eric.demo.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

public class MapperExtPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        //创建一个自定义的逻辑查询
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "logicDeleteById"));
        update.addAttribute(new Attribute("parameterType", introspectedTable.getColumn("id").getFullyQualifiedJavaType().getFullyQualifiedName()));
        update.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " \n \tset is_del=1 \n \t\twhere id=#{id,jdbcType=" + introspectedTable.getColumn("id").getJdbcTypeName() + "}"));
        document.getRootElement().addElement(update);
        //创建一个根据map查询sql模板
        XmlElement findList = new XmlElement("select");
        findList.addAttribute(new Attribute("id", "findList"));
        findList.addAttribute(new Attribute("parameterType", "map"));
        findList.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        findList.addElement(new TextElement("  select <include refid='Base_Column_List' /> \n\t from " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " \n \t <include refid='Base_Query_Map_Condition' />"));

        //创建一个通过实体查询的sql
        XmlElement findByVo = new XmlElement("select");
        findByVo.addAttribute(new Attribute("id", "findByVo"));
        findByVo.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        findByVo.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        findByVo.addElement(new TextElement("  select <include refid='Base_Column_List' /> \n\t from " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " \n \t <include refid='Base_Query_Vo_Condition' />"));


        createMapCon(document, introspectedTable);
        createVoCon(document, introspectedTable);
        document.getRootElement().addElement(findList);
        document.getRootElement().addElement(findByVo);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private void createMapCon(Document document, IntrospectedTable introspectedTable) {
        XmlElement baseQueryMapConditionElement = new XmlElement("sql");
        baseQueryMapConditionElement.addAttribute(new Attribute("id",
                "Base_Query_Map_Condition"));

        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "where"));
        trimElement.addAttribute(new Attribute("prefixOverrides", "and | or"));

        // alexgaoyh begin equal = 相等部分的 操作
        StringBuilder sbEqual = new StringBuilder();
        createEqu(introspectedTable, trimElement, sbEqual);
        // alexgaoyh end equal = 相等部分的 操作

        // alexgaoyh begin greater 大于
        StringBuilder sbGreater = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sbGreater.setLength(0);
            sbGreater.append("myGreater_" + introspectedColumn.getJavaProperty());
            sbGreater.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sbGreater.toString())); //$NON-NLS-1$

            sbGreater.setLength(0);
            sbGreater.append(" AND ");
            sbGreater.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sbGreater.append(" &gt; "); //$NON-NLS-1$
            sbGreater.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "myGreater_"));
            valuesNotNullElement.addElement(new TextElement(sbGreater.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        // alexgaoyh end greater 大于

        // alexgaoyh begin lesser 小于
        StringBuilder sbLesser = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sbLesser.setLength(0);
            sbLesser.append("myLesser_" + introspectedColumn.getJavaProperty());
            sbLesser.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sbLesser.toString())); //$NON-NLS-1$

            sbLesser.setLength(0);
            sbLesser.append(" AND ");
            sbLesser.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sbLesser.append(" &lt; "); //$NON-NLS-1$
            sbLesser.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "myLesser_"));
            valuesNotNullElement.addElement(new TextElement(sbLesser.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        // alexgaoyh end lesser 大于

        // alexgaoyh begin like 相似部分的 操作-前后完全匹配相似
        StringBuilder sblike = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sblike.setLength(0);
            sblike.append("myLike_" + introspectedColumn.getJavaProperty());
            sblike.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sblike.toString())); //$NON-NLS-1$

            sblike.setLength(0);
            sblike.append(" AND ");
            sblike.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sblike.append(" like "); //$NON-NLS-1$
            sblike.append("CONCAT('%', " + MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "myLike_") + ",'%' )");
            valuesNotNullElement.addElement(new TextElement(sblike.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        // alexgaoyh end like 相似部分的 操作-前后完全匹配相似

        // alexgaoyh begin like 相似部分的 操作-前端匹配
        StringBuilder sblikeStart = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sblikeStart.setLength(0);
            sblikeStart.append("myLikeStart_" + introspectedColumn.getJavaProperty());
            sblikeStart.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sblikeStart.toString())); //$NON-NLS-1$

            sblikeStart.setLength(0);
            sblikeStart.append(" AND ");
            sblikeStart.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sblikeStart.append(" like "); //$NON-NLS-1$
            sblikeStart.append("CONCAT('%', " + MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "myLikeStart_") + ")");
            valuesNotNullElement.addElement(new TextElement(sblikeStart.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        // alexgaoyh begin like 相似部分的 操作-后端匹配
        StringBuilder sblikeEnd = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sblikeEnd.setLength(0);
            sblikeEnd.append("myLikeEnd_" + introspectedColumn.getJavaProperty());
            sblikeEnd.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sblikeEnd.toString())); //$NON-NLS-1$

            sblikeEnd.setLength(0);
            sblikeEnd.append(" AND ");
            sblikeEnd.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sblikeEnd.append(" like "); //$NON-NLS-1$
            sblikeEnd.append("CONCAT( " + MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "myLikeEnd_") + ",'%' )");
            valuesNotNullElement.addElement(new TextElement(sblikeEnd.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        // alexgaoyh end like 相似部分的 操作-后端匹配

        // alexgaoyh begin in 语句
        StringBuilder sbIn = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sbIn.setLength(0);
            sbIn.append("myIn_" + introspectedColumn.getJavaProperty());
            sbIn.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sbIn.toString())); //$NON-NLS-1$
            sbIn.setLength(0);
            sbIn.append(" AND ");
            sbIn.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sbIn.append(" in "); //$NON-NLS-1$
            sbIn.append("<foreach item=\"item\" index=\"index\" collection=\"" + "myIn_" + introspectedColumn.getJavaProperty() + "\" open=\"(\" separator=\",\" close=\")\"> #{item} </foreach>");
            valuesNotNullElement.addElement(new TextElement(sbIn.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
        baseQueryMapConditionElement.addElement(trimElement);
        document.getRootElement().addElement(baseQueryMapConditionElement);
    }

    private void createVoCon(Document document, IntrospectedTable introspectedTable) {
        XmlElement baseQueryMapConditionElement = new XmlElement("sql");
        baseQueryMapConditionElement.addAttribute(new Attribute("id",
                "Base_Query_Vo_Condition"));
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "where"));
        trimElement.addAttribute(new Attribute("prefixOverrides", "and | or"));
        // alexgaoyh begin equal = 相等部分的 操作
        StringBuilder sbEqual = new StringBuilder();
        createEqu(introspectedTable, trimElement, sbEqual);
        // alexgaoyh end equal = 相等部分的 操作
        baseQueryMapConditionElement.addElement(trimElement);
        document.getRootElement().addElement(baseQueryMapConditionElement);
    }

    private void createEqu(IntrospectedTable introspectedTable, XmlElement trimElement, StringBuilder sbEqual) {
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sbEqual.setLength(0);
            sbEqual.append(introspectedColumn.getJavaProperty());
            sbEqual.append(" != null"); //$NON-NLS-1$
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sbEqual.toString())); //$NON-NLS-1$
            sbEqual.setLength(0);
            sbEqual.append(" AND ");
            sbEqual.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sbEqual.append(" = "); //$NON-NLS-1$
            sbEqual.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            valuesNotNullElement.addElement(new TextElement(sbEqual.toString()));
            trimElement.addElement(valuesNotNullElement);
        }
    }
}
