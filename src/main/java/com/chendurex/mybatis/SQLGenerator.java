package com.chendurex.mybatis;

import com.chendurex.mybatis.anaotation.Condition;
import com.chendurex.mybatis.util.Utils;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.web.module.hr.time.attendance.common
 * @date 2016/11/22 20:16
 */
public class SQLGenerator {

    /**
     * 生成resultMapper文件
     * @param clazz
     * @return
     */
    public static String generatorResultMapper(Class<?> clazz) {
        Set<Field> fields = Utils.getAllFields(clazz);
        StringBuilder generator = new StringBuilder(fields.size() * 10);
        String resultMap = String.format("<resultMap id=\"%s\" type=\"%s\">",
                Introspector.decapitalize(clazz.getSimpleName()) + "Map", clazz.getName());
        generator.append(resultMap).append(Utils.nextLine);
        for (Field field : fields) {
            String result = String.format("<result column=\"%s\" property=\"%s\" javaType=\"%s\" jdbcType=\"%s\"/>",
                    TypeAlias.getField(clazz,field.getName()), field.getName(),
                    TypeConvert.getType(field.getType()).getName(), JdbcTypeMap.TYPE.get(TypeConvert.getType(field.getType())));
            generator.append("    ").append(result).append(Utils.nextLine);
        }
        generator.append("</resultMap>");
        return generator.toString();
    }

    /**
     * 生成查询SQL
     * @param clazz
     * @return
     */
    public static String generatorSelectSQL(Class<?> clazz) {
        return "select "
                + Utils.nextSpace
                + Utils.javaFieldToTableField(clazz)
                + Utils.nextLine
                + "from "
                + TypeAlias.getTable(clazz);
    }

    /**
     * 生成插入SQL
     * @param clazz
     * @return
     */
    public static String generatorInsertSQL(Class<?> clazz) {
        return  "insert into "
                + TypeAlias.getTable(clazz)
                + Utils.nextLine
                + "("
                + Utils.nextSpace
                + Utils.javaFieldToTableField(clazz)
                + Utils.nextLine
                + ")"
                + Utils.nextLine
                + "values"
                + Utils.nextLine
                + "("
                + Utils.nextSpace
                + Utils.tableFieldToMapperFiled(Utils.getAllFieldsAndConvert(clazz))
                + Utils.nextLine
                + ")";
    }

    /**
     * 生成带条件的SQL
     * @param clazz
     * @return
     */
    public static String generatorCondSQL(Class<?> clazz, String ... param) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorSelectSQL(clazz))
                .append(Utils.nextLine)
                .append("where")
                .append(Utils.nextLine);
        for (String p : param) {
            sb.append(Utils.javaFieldConvertCondSQL(p, true, true));
        }
        return sb.toString();
    }


}
