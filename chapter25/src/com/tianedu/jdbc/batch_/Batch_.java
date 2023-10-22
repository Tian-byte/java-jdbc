package com.tianedu.jdbc.batch_;

import com.tianedu.jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author tian
 * 演示Java的批处理
 */
public class Batch_ {
    @Test
    //传统的方法添五千条数数据到admin2
    public void noBatch() throws SQLException {
        //得到连接
        Connection connection = JDBCUtils.getConnection();
        //sql
        String sql = "insert into admin2 values(null,?,?)";
        //使用connect创建一个prepareStatement()
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始时间");
        long  start = System.currentTimeMillis(); // 开始时间
        for (int i = 0; i<= 5000; i++){
            preparedStatement.setString(1,"jack"+i);
            preparedStatement.setString(2,"666");
            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("传统的方式 耗时="+ (end - start));
        //关闭连接
        JDBCUtils.close(null,preparedStatement,connection);

    }
    //使用批量处理方式添加数据
    @Test
    public void batch() throws SQLException {
        //获得连接
        Connection connection = JDBCUtils.getConnection();
        //写sql语句
        String sql = "insert into admin3 values(null,?,?)";
        //获得preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1,"tom+i");
            preparedStatement.setString(2,"777");
         //   preparedStatement.executeUpdate(); // 执行
            //将sql语句加入到批处理包中
             preparedStatement.addBatch();
             /*
             1.第一创建ArrayList —— elementDate => object[]
             2.elementDate => object[] 就会存放我们预处理的sql语句
             3.当elementDate满后，就按照1.5扩容
             4.当添加到指定的值后就执行批量处理
             5.批量处理会减少发生sql 语句的网络开销，而且减少编码的次数，因此效率高
              */
            //当有一千条记录时候在批量执行
            if ((i+1) % 1000 == 0) { // 说明满一千条 批量执行
                preparedStatement.executeBatch();
                //清空一把
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批量执行方式  耗时 = " + (end - start));
        //关闭连接
        JDBCUtils.close(null,preparedStatement,connection);
    }
}
