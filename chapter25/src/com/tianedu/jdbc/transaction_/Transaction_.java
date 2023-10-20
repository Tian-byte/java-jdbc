package com.tianedu.jdbc.transaction_;

import com.tianedu.jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author tian
 * 演示在JDBC中如何使用事务

 */
public class Transaction_ {
    @Test
    public void noTransaction_(){
        //操作转账的业务
        //1.得到连接
        Connection connection = null;
        //2.组织一个sql语句
        String sql = "update account  set balance = balance -100 where id = 1";
        String sql2 = "update account  set balance = balance + 100 where id = 2";
        // 创建 PreparedStatement 对象
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(); // 执行第一条sql语句

            int i = 1/0;   // 抛出异常 下面的代码不在执行
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate(); // 执行第二条 sql2 语句
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            JDBCUtils.close(null,preparedStatement,connection);
        }
    }
    // 事务来解决
    @Test
    public void useTransaction() {
        //1. 连接事务
        Connection connection = null;
        //2.组织sql
        String sql = "update account  set balance = balance -100 where id = 1";
        String sql2 = "update account  set balance = balance + 100 where id = 2";
        //3.创建PrepareStatement 对象
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            // 将connection 设置为不自动提交
            connection.setAutoCommit(false);   //相当于开始事务
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(); // 执行sql

            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate(); //执行sql2

            //这里提交事务
            connection.commit();

        } catch (SQLException e) {
            //这里我们可以进行回滚，即撤销执行的sql
            //默认回滚到事务开始的状态
            System.out.println("执行发生了异常，撤销执行的sql");
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
            }
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(null,preparedStatement,connection);
        }


    }
}
