package com.chendurex;

import com.chendurex.mybatis.FieldGenerator;
import com.chendurex.mybatis.TypeAlias;
import org.junit.Test;

/**
 * @author chen
 * @description
 * @pachage com.chendurex
 * @date 2016/12/29 19:00
 */
public class MapperTest {
    @Test
    public void testResultMapper() {
        System.out.println(FieldGenerator.generatorResultMapper(TestBean.class));
    }

    @Test
    public void testSelectSQL() {
        System.out.println(FieldGenerator.generatorSelectSQL(TestBean.class));
    }

    @Test
    public void testInsertSQL() {
        System.out.println(FieldGenerator.generatorInsertSQL(TestBean.class));
    }

    @Test
    public void testTableAlias() {
        TypeAlias.registryTable(TestBean.class, "test_table");
        System.out.println(FieldGenerator.generatorSelectSQL(TestBean.class));
    }

    @Test
    public void testFieldAlias() {
        TypeAlias.registryField(TestBean.class, "sys7", "test_table");
        testResultMapper();
        testSelectSQL();
        testInsertSQL();
    }
}
