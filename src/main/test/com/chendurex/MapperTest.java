package com.chendurex;

import com.chendurex.mybatis.SQLGenerator;
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
        System.out.println(SQLGenerator.generatorResultMapper(TestBean.class));
    }

    @Test
    public void testSelectSQL() {
        System.out.println(SQLGenerator.generatorSelectSQL(TestBean.class));
    }

    @Test
    public void testInsertSQL() {
        System.out.println(SQLGenerator.generatorInsertSQL(TestBean.class));
    }

    @Test
    public void testTableAlias() {
        TypeAlias.registryTable(TestBean.class, "test_table");
        System.out.println(SQLGenerator.generatorSelectSQL(TestBean.class));
    }

    @Test
    public void testFieldAlias() {
        TypeAlias.registryField(TestBean.class, "sys7", "test_table");
        testResultMapper();
        testSelectSQL();
        testInsertSQL();
    }

    @Test
    public void testGeneratorWhereSQL() {
        System.out.println(SQLGenerator.generatorWhereSQL(TestBean.class, "sys7", "sys8"));
    }

    @Test
    public void testGeneratorUpdateSQL() {
        System.out.println(SQLGenerator.generatorUpdateSQL(TestBean.class, "sys7", "sys8"));
    }

    @Test
    public void testGeneratorMultiInsertSQL() {
        System.out.println(SQLGenerator.generatorMultiInsertSQL(TestBean.class));
    }
}
