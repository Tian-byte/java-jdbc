package com.tianedu.jdbc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author tian
 * 这是一个工具类 完成MySQL的连接和关闭资源
 *
 */
public class JDBCUtils {
    // 定义相关的属性（4个）,因为只需要一份，我们做出Static
    private static  String user;     // 用户名
    private static String password;  // 密码
    private static String url;     //url
    private static String driver;  //驱动名称
    //在static 代码块去初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\mysql.properties"));
            //读取相关的属性值
            user =  properties.getProperty("user");
            password = properties.getProperty("password");
            url =  properties.getProperty("url");
            driver =  properties.getProperty("driver");
        } catch (IOException e) {
            //在实际开发中，我们可以这样处理
            //1.将编译异常转换成 运行异常
            //2.这里调用者可以选择捕获该异常，也可以选择默认异常，比较方便。
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }
//   连接数据库 返回一个Connection
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
//            throwables.printStackTrace();
            //将编译异常转换成 运行异常，调用者可以捕获该异常，也可以选择默认处理该异常，比较方便
            throw new RuntimeException(e);
        }
    }
    //关闭相关的资源
    /*
    1.ResultSet 结果集
    2.Statement 或者 PreparedStatement
    3.关闭Connection
    4.如果需要关闭资源，就传入对象，否则传入null
     */
    public static void  close(ResultSet set, Statement statement,Connection connection){
        //判断是否为null
        try {
            if(set != null){
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            //将编译异常转成运行异常抛出
            throw new RuntimeException(e);
        }
    }
}
