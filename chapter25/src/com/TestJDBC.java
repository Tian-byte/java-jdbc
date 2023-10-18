package com;

/**
 * @author tian
 */
public class TestJDBC {
    public static void main(String[] args) {
        // 完成对mysql 的操作
   JdbcInterface jdbcInterface =  new MysqlJdbcImpl();
   jdbcInterface.getConnection();
   jdbcInterface.crud();
   jdbcInterface.close();


        System.out.println("===================");
        jdbcInterface =    new OracleJdbcImpl();
        jdbcInterface.getConnection();
        jdbcInterface.crud();
        jdbcInterface.close();
    }
}
