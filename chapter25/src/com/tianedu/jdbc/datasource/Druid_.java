package com.tianedu.jdbc.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author tian
 */
public class Druid_ {
    @Test
    //测试德鲁伊的使用
    public void testDruid() throws Exception {
         // 1.加入Druid jar 包
        // 加入配置文件 将该文件拷贝到src目录下  druid.properties
        //3.创建一个 properties 对象，用来读取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\druid.properties"));
        //4.创建一个指定参数的数据库连接池,德鲁伊的连接池
        DataSource dataSource =
                DruidDataSourceFactory.createDataSource(properties);
        //拿到连接
        Connection connection = dataSource.getConnection();
        System.out.println("连接成功");
        connection.close();
    }
}
