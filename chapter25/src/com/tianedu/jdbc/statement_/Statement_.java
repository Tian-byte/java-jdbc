package com.tianedu.jdbc.statement_;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author tian
 * 演示statement 的注入问题
 */
@SuppressWarnings({"all"})
public class Statement_ {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //让用户输入管理员姓名和密码
        System.out.print("请输入管理员的名字：");
        String admin_name = scanner.nextLine(); //next当接受到空格或者 ' 表示结束
        System.out.print("请输入管理员的密码：");
        String admin_pwd = scanner.nextLine();
        // 通过Properties 对象获取配置文件的信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        // 获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        //1.注册驱动
        Class.forName(driver);

        //2.得到连接
        Connection connection = DriverManager.getConnection(url,user,password);
        //3.得到Statement
        Statement statement = connection.createStatement();
        //4.组织sql
        String sql = "select name,pwd from admin where name = '"
                +admin_name+"' and pwd= '"+admin_pwd+"'" ;
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            //如果查询到一条记录，则说明该管理员存在
            System.out.println("登录成功");
        } else  {
            System.out.println("对不起，登录失败");
        }
        // 关闭连接
        resultSet.close();
        statement.close();
        connection.close();
    }

}
