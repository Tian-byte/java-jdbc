package com.tianedu.jdbc;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author tian
 * 分析java 连接MySQL的物种方式
 */
public class JdbcConn {
    @Test
    public void connect01() throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/hsp_db03";
        // 将用户名和密码放入到Properties 对象
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "tian");
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);
    }
    //方式2
    @Test
    public void connect02() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //使用反射加载Driver 动态加载，更加灵活，减少依赖性
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/hsp_db03";
        // 将用户名和密码放入到Properties 对象
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "tian");

        Connection connect = driver.connect(url, properties);
        System.out.println("方式2=" + connect);
    }
    // 方式3 使用DriverManager 替代 Driver 进行统一管理
    @Test
    public void connect03() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // 使用反射 加载Driver
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver  = (Driver) aClass.newInstance();
        //创建 url 和 user 和 password
        String url = "jdbc:mysql://localhost:3306/hsp_db03";
        String user = "root";
        String password = "tian";
        DriverManager.registerDriver(driver);  //注册Driver 驱动
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("第三种方式" + connection);
    }
    // 方式4   使用Class.forName 自动完成注册，简化代码
    // 使用最多
    @Test
    public void  connect04() throws ClassNotFoundException, SQLException {
       // 使用反射加载 Driver 类
        // 在加载Driver类时，完成注册
        /*
            源码： 1.静态代码块，在类加载时，会执行一次
            2.DriverManager.registerDriver(new Driver());
            3.因此 加载和注册Driver 的工作已经完成
            static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }
         */

        Class.forName("com.mysql.jdbc.Driver") ;  //如果没有这句也可以执行，建议写上更加明确
        // MySQL 驱动 5.1.6 可以无需Class.forName("com.mysql.jdbc.Driver");
        // 从 jdk1.5 以后使用Jdbc4 不在需要显示调用class.forName() 注册驱动而已自动调用驱动
        // jar包下META-INF\services\java.sql.Driver文本种的类 名称去注册
        String url = "jdbc:mysql://localhost:3306/hsp_db03";
        String user = "root";
        String password = "tian";
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println("第四种方式" + connection);
    }
    @Test
    // 方式5，在方式4的基础上改进，增加配置文件，让信息连接MySQL更加灵活
    public void connect05() throws IOException, ClassNotFoundException, SQLException {
        //通过Properties 对象获取配置文件的信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        // 获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        Class.forName(driver); //建议写上 更加明确
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式5" + connection);
    }
}
