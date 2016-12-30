package com.chendurex.mybatis.field;

import com.chendurex.mybatis.util.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chen
 * @description
 * @pachage com.chendurex.mybatis.field
 * @date 2016/12/30 9:38
 */
public class ProConstGenerator {
    private static final Set<String> EXCLUDES = new HashSet<>();

    /**
     * 打印class类的属性
     * @param clazz
     * @return
     */
    public static String create(Class<?> clazz) {
        Set<Field> fields = Utils.getAllFields(clazz);
        StringBuilder sb = new StringBuilder(fields.size() * 10);
        for (Field field : fields) {
            if (!EXCLUDES.add(field.getName())) {
                continue;
            }
            sb.append(Utils.nextSpace)
                    .append("String ")
                    .append(Utils.fieldToConst(field.getName()))
                    .append(" = ")
                    .append("\"")
                    .append(Utils.javaFieldToTableField(field.getName()))
                    .append("\"")
                    .append(";");
        }
        return sb.toString();
    }

    /**
     * 打印class的属性并且生成一个java字符
     * @param clazz
     * @param pck
     * @return
     */
    public static String create(Class<?> clazz, String pck) {
        return createJavaContent(pck, clazz.getSimpleName(), create(clazz));
    }

    /**
     * 基于包，把所有的class属性打印出来
     * @param pck
     * @return
     */
    public static String create(String pck) {
        Set<String> allClasses = Utils.getAllClasses(pck);
        try {
            StringBuilder sb = new StringBuilder(allClasses.size() * 100);
            for (String clz : allClasses) {
                sb.append(create(Class.forName(clz)));
            }
            return sb.toString();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createJavaContent(String pck, String name, String content) {
        return "package"
                + " "
                + pck
                + ";"
                + Utils.nextLine
                + Utils.nextLine
                + Utils.nextLine
                + "public "
                + "interface "
                + name.concat("Const")
                + " {"
                + Utils.nextLine
                + content
                + Utils.nextLine
                + "}";
    }

    public static void write(String fd, String pck) {
        String filePath = fd.substring(0, fd.lastIndexOf("."));
        String fileName = fd.substring(fd.lastIndexOf(".") + 1);
        writeToFile(Utils.getFilePath(filePath, fileName),
                createJavaContent(filePath, fileName, create(pck)));
    }

    public static void write(Class<?> clazz) {
        writeToFile(Utils.getFilePath(clazz), create(clazz, clazz.getPackage().getName()));
    }

    public static void write(Class<?> clazz, String pck) {
        writeToFile(Utils.getFilePath(pck, clazz.getSimpleName()), create(clazz, pck));
    }

    private static void writeToFile(String filePath, String content) {
        try {
            FileUtils.write(new File(filePath), content);
        } catch (IOException e) {
            throw new RuntimeException("文件写入失败：", e);
        }
    }
}
