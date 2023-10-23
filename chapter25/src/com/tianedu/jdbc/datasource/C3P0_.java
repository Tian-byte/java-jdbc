package com.tianedu.jdbc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author tian
 *
 * 演示c3p0的使用
 */
public class C3P0_ {
    @Test

    //方式1  相关的参数在程序中指定  user url  password 等
    public void testC3p0_01() throws Exception {
        //1.创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        //2.通过配置文件获取相关信息 MySQL.properties 获取相关的连接信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        //获取相关的属性
        String   user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        //给数据源 comboPooledDateSource  设置相关的参数
        //连接的管理是由 comboPooledDataSource帮我们管理的
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        // 设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        //测试连接池的效率
        long  start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection();//这个方法是从 DateSource 接口实现的
//            System.out.println("连接成功");
            connection.close();
        }
       long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    // 方式2 使用配置文件模板来完成
    // 将c3p0 拷贝到 src 目录下
    //该文件指定了 连接数据库和连接池的线管参数
    @Test
    public void testC3PO_02() throws SQLException {
        //使用配置文件
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("tian");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println(end -start);
    }
}
