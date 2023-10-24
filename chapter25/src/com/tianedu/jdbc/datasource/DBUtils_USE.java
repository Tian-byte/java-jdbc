package com.tianedu.jdbc.datasource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author tian
 */
@SuppressWarnings({"all"})
public class DBUtils_USE {
    @Test
  //使用apache-DBUtils 工具类 + druid  的方式完成对表的crud操作
    public void testQueryMany() throws SQLException {   //返回结果是多行的情况

        //1. 得到连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.使用DBUtiles 类和接口  先引入德鲁伊的jar文件，加入到本project
        //3.创建一个 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回ArrayList 结果集
        //String  sql = "select * from actor where id >= ?";
        //注意 sql 语句也可以查询部分列
        String sql = "select id,name from actor where id >= ?";


        //1.query 方法就是执行一个sql语句得到一个resultset -- 分装到 --》 ArrayList 集合中然后返回
        //2.返回集合
        //3.connection: 连接
        //4.sql 执行sql语句
        //5.ew BeanListHandler<>(Actor.class)  再将resultset -》 actor 对象分装到 ArrayList
        //底层 使用反射机制 去获取actor 类的属性，然后进行封装
        //6. 1 是传递给sql 的？ 的 可以有多个值，因为它是一个可变参数 Object... parames
        //7.底层 得到的resultSet 会在query 关闭，关闭PrepareStatment
        List<Actor> list =
                queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), 1);
        System.out.println("输出集合的信息");
        for (Actor actor : list){
            System.out.print(actor);
        }
        // 释放资源
        JDBCUtilsByDruid.close(null,null,connection);
    }
    // 演示 apache - dubtlis + druid 完成 返回的结果 是单行记录（单个对象）
    @Test
    public void testQuerySingle() throws SQLException {
        //1.得到连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.创建QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //3.sql 语句  执行相关方法，返回单个对象
        String sql = "select * from actor where id = ?";
        //因为我们知道返回的是单行记录  单个记录，使用Hander 是 BeanHandler
        Actor actor = queryRunner.query(connection, sql, new BeanHandler<>(Actor.class), 2);
        System.out.println(actor);
        JDBCUtilsByDruid.close(null,null,connection);

    }
    // 演示apache-dubtils + druid 完成查询结果是单行单列 - 返回的就是一个Object
    // Scalar 胆量
    @Test
    public void testScalar() throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        //执行相关方法，返回单行单列，返回的就是Obeject
        String sql = "select name from actor where id = ?";
        //因为返回的是单行单列，返回的是ScalarHandler
        Object obj= queryRunner.query(connection, sql, new ScalarHandler(), 2);
        System.out.println(obj);
        JDBCUtilsByDruid.close(null,null,connection);
    }

    //演示 apache-dbutils + druid 完成 dml(update insert delect)
    @Test
    public void testDML() throws SQLException {
        //得到连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //创建queryRunner
        QueryRunner queryRunner = new QueryRunner();

        //4.这里我们可以组织sql语句来完成 update insert delect
        //String sql = "update actor set name = ? where id = ?";



        //String sql = "insert into actor values(null,?,?,?,?)";

        String sql =" delete from actor where  id = ?";

        // 1.我们执行dml 操作使用 方法是 queryRunner.update()
        //2.返回值是受影响的行数 affectedRow 受影响的行

        //int affectedRow = queryRunner.update(connection, sql, "林青霞","女","1917-10-10","116");
        int affectedRow = queryRunner.update(connection,sql,100 );
        System.out.println(affectedRow > 0 ? "执行成功":"执行没有影响数据库·");
        //释放资源
        JDBCUtilsByDruid.close(null,null,connection);
    }
}