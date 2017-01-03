package com.chendurex;

import com.chendurex.mybatis.util.Utils;
import org.junit.Test;

/**
 * @author chen
 * @description
 * @pachage com.chendurex
 * @date 2016/12/30 14:26
 */
public class utilsTest {
    @Test
    public void testGetAllClasses() {
        Utils.getAllClasses("com.chendurex.mybatis.field");
    }

    @Test
    public void testJavaFieldConvertCondSQL() {
        System.out.println(Utils.javaFieldConvertCondSQL("dateFrom"));
    }
}
