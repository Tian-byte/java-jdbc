package com.tianedu.jdbc.preparedstatement_;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author tian
 *
 * 演示 preparedStatement使用
 */
@SuppressWarnings({"all"})
public class PreparedStatement_ {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //让用户输入管理员姓名和密码
        System.out.print("请输入管理员的名字：");
        String admin_name = scanner.nextLine(); //next当接受到空格或者 ' 表示结束
        System.out.print("请输入管理员的密码：");
        String admin_pwd = scanner.nextLine();
        //通过Properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));

        //获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");

        // 注册驱动
        Class.forName(driver);
        //得到连接
        Connection connection = DriverManager.getConnection(url,user,password);
        // 组织sql sql语句的 ？ 相当于占位符
        String sql = "select name,pwd from admin where name = ? and pwd = ?";
        //3.2 preparedStatement 对象实现了 preparedStatement 接口的实现类的对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //3.3 给？赋值
        preparedStatement.setString(1,admin_name);
        preparedStatement.setString(2,admin_pwd);
        //4.执行select 语句使用 executeQuery
        // 如果执行的是 dml(update,insert,delete) executeUpdate()
        //这里执行 executeQuery 不要在写sql
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
       resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
