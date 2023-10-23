package com.tianedu.jdbc.datasource;

import com.tianedu.jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author tian
 */
public class ConQuestion {
    //代码 连接mysql 五千此
    @Test
    public void testCon(){

        // 看看 关闭connections 需要多久时间
        long   start = System.currentTimeMillis();
        for (int i = 0; i < 5000 ; i++) {
            //使用传统的JDBC连接mysql
            // 获得连接
            Connection connection = JDBCUtils.getConnection();
            // 写sql 做一些工作  得到PreparedStatement  发生sql
            //......
            //出现异常 to many connections 导致服务器崩溃

            // 关闭
            JDBCUtils.close(null,null,connection);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);  // 耗时间
    }
}
