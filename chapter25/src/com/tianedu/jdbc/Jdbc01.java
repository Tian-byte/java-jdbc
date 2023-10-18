package com.tianedu.jdbc;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author tian
 * 这是第一个jdbc 程序 完成简单的操作
 */
public class Jdbc01 {

    public static void main(String[] args) throws SQLException {
        // 前置工作 在项目下创建一个文件夹 比如 libs
        // 将MySQL.jar 拷贝到该目录下，点击add to project 加入到项目种才可以使用
        //1. 注册驱动
        Driver driver = new Driver();
        //2.得到连接
        String url  = "jdbc:mysql://localhost:3306/hsp_db03";
        // 将用户名和密码放入到Properties 对象
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","tian");
        Connection connect = driver.connect(url, properties);
        //3.执行sql
        String sql = "insert into actor values(null,'刘德华','男','1970-11-11','110')";
        //用于执行静态的sql 语句并返回其生成的结果的对象
        Statement statement = connect.createStatement();
        int rows =statement.executeUpdate(sql);
        // 如果dml语句，返回的就是影响的行数
        System.out.println(rows > 0 ? "成功":"失败");
        //4.关闭连接资源
        statement.close();
        connect.close();
    }
}
