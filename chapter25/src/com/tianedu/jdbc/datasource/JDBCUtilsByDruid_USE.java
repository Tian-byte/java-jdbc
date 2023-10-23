package com.tianedu.jdbc.datasource;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author tian
 */
@SuppressWarnings({"all"})
public class JDBCUtilsByDruid_USE {
    @Test
    public void testSelect(){
        System.out.println("使用德鲁伊的方式完成");
    // 1.得到连接
        Connection connection = null;
        //2.组织一个sql
        String sql = "select * from actor where id >= ?";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //3.创建PreparedStatement 对象
        try {
            connection = JDBCUtilsByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,1);
            set = preparedStatement.executeQuery();

//            connection.close();
            //遍历该结果集
            while (set.next()){
                int id = set.getInt("id");
                String name = set.getString("name");  //getName()
                String sex = set.getString("sex");
                Date borndate = set.getDate("borndate");
                String phone = set.getString("phone");
                System.out.println(id + "\t" + name + "\t" + sex + "\t" + borndate + "\t" + phone );


            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtilsByDruid.close(set,preparedStatement,connection);
        }
    }
    //使用土方法来解决 ResultSet => 封装 => ArrayList
    @Test
    public ArrayList<Actor> testSelectToArrayList(){
        System.out.println("使用德鲁伊的方式完成");
        // 1.得到连接
        Connection connection = null;
        //2.组织一个sql
        String sql = "select * from actor where id >= ?";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //创建一个ArrayList
        ArrayList<Actor> list = new ArrayList<>();  // 创建ArrayList 对象，存放actor 对象


        //3.创建PreparedStatement 对象
        try {
            connection = JDBCUtilsByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,1);
            set = preparedStatement.executeQuery();

//            connection.close();
            //遍历该结果集
            while (set.next()){
                int id = set.getInt("id");
                String name = set.getString("name");  //getName()
                String sex = set.getString("sex");
                Date borndate = set.getDate("borndate");
                String phone = set.getString("phone");
                //把得到的resultSet 分装到 Actor对象  放入到List 集合
                list.add(new Actor(id,name,sex,borndate,phone));
            }

            System.out.println("list 集合数据=" + list);
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtilsByDruid.close(set,preparedStatement,connection);
        }
        //因为ArrayList 和 connection 没有任何关联 所以该集合可以复用
        return list;
    }
}
