package com.chendurex.mybatis;

import com.chendurex.mybatis.util.Utils;

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
     * 生成批量插入的SQL
     * @param clazz
     * @param param 可选项，如果传入第一个参数则表示集合的标识，第二个参数表示迭代标识
     * @return
     */
    public static String generatorMultiInsertSQL(Class<?> clazz, String ... param) {
        String source = "list";
        String prefix = "item";
        if (param != null && param.length > 0) {
            source = param[0];
            if (param.length > 1) {
                prefix = param[1];
            }
        }
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
                + "<foreach collection=\"" + source + "\" item=\""+ prefix + "\" separator=\",\">"
                + Utils.nextLine
                + "("
                + Utils.nextSpace
                + Utils.tableFieldToMapperFiled(Utils.getAllFieldsAndConvert(clazz), prefix)
                + Utils.nextLine
                + ")"
                + Utils.nextLine
                + "</foreach>";
    }

    public static String generatorUpdateSQL(Class<?> clz, String ... param) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ")
                .append(TypeAlias.getTable(clz))
                .append(Utils.nextLine)
                .append("<set>")
                .append(Utils.nextLine);
        for (String p : param) {
            sb.append(Utils.javaFieldConvertUpdateSQL(p));
        }
        return sb.substring(0, sb.lastIndexOf(",")).concat("\r\n  </if>\r\n</set>");
    }

    /**
     * 生成带条件的SQL
     * @param clazz
     * @return
     */
    public static String generatorWhereSQL(Class<?> clazz, String ... param) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorSelectSQL(clazz))
                .append(Utils.nextLine)
                .append("where")
                .append(Utils.nextLine);
        for (String p : param) {
            sb.append(Utils.javaFieldConvertCondSQL(p));
        }
        return sb.substring(0, sb.lastIndexOf("and")).concat("\r\n</if>");
    }

}
