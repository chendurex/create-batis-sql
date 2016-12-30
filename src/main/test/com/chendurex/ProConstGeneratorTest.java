package com.chendurex;

import com.chendurex.mybatis.field.ProConstGenerator;
import org.junit.Test;

/**
 * @author chen
 * @description
 * @pachage com.chendurex
 * @date 2016/12/30 9:50
 */
public class ProConstGeneratorTest {
    @Test
    public void testProConstCreate() {
        System.out.println(ProConstGenerator.create(TestBean.class));
    }

    @Test
    public void testProConstCreateWith() {
        System.out.println(ProConstGenerator.create(TestBean.class, TestBean.class.getPackage().getName()));
    }

    @Test
    public void testWriteWithPackage() {
        ProConstGenerator.write(TestBean.class, TestBean.class.getPackage().getName());
    }

    @Test
    public void testWrite() {
        ProConstGenerator.write(TestBean.class);
    }

    @Test
    public void testWriteBasePackage() {
        ProConstGenerator.write("com.chendurex.mybatis.field.TTT", "com.chendurex.mybatis.field");
    }

}
