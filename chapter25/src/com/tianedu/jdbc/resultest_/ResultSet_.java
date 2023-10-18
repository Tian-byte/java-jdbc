package com.tianedu.jdbc.resultest_;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author tian
 *
 * 演示select 语句返回一个resultset 并取出结果
 */
@SuppressWarnings({"all"})
public class ResultSet_ {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        //通过Properties 对象获取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        // 获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        Class.forName(driver); //建议写上 更加明确
        Connection connection = DriverManager.getConnection(url, user, password);
        //得到Statement
        Statement statement = connection.createStatement();
        //组织sql 语句
        String sql = "select id,name,sex,borndate from actor";
        //执行给定的sql语句，该语句返回单个 ResultSet对象
        ResultSet resultSet = statement.executeQuery(sql);
        // 使用where 循环取出数据
        while (resultSet.next()) {
            // 让光标向后移动 如果没有更多的记录则返回false
       int id =  resultSet.getInt(1);
            String name = resultSet.getString(2);// 获取该行第二列
            String sex = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            System.out.println(id + "\t" + name + "\t" + sex + "\t" + date);
            // 获取该行的对第一列
        }
        // 关闭连接
        resultSet.close();
        connection.close();
        statement.close();

    }
}
