package com.tianedu.jdbc.utils;

import java.io.FileInputStream;
import java.io.IOException;
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
//   连接数据库 返回一个


}
