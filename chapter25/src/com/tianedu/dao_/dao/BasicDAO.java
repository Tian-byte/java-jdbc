package com.tianedu.dao_.dao;

import com.tianedu.dao_.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author tian
 *
 * 开发BasicDAO是其他DAO的父类
 */
public class BasicDAO<T> {  //泛型指定具体类型

   private QueryRunner qr = new QueryRunner();
   //开发通用的dml方法，针对任意的表
    public int update(String sql,Object...parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);  //将一个编译异常转换为运行异常 抛出
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //返回多个对象（即查询 的结果是多行），针对任意表

    /**
     *
     * @param sql    sql语句 ，可以有 ？
     * @param clazz  传入一个类的class 对象  比如 Actor.class
     * @param parameters  传入问号  具体的值，可以是多个
     * @return   根据 Actor.class 返回对应的 ArrayList 集合
     */
    public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return  qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
    //查询单行结果的通用方法
    public T querySingle(String sql,Class<T> clazz,Object... parameters){
        Connection connection =null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
          return qr.query(connection,sql,new BeanHandler<T>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
    //查询单行单列的方法，即返回单值的方法
    public Object queryScalar(String sql,Object... parameters) {
        Connection connection = null;
        try {
            connection  = JDBCUtilsByDruid.getConnection();
            return qr.query(connection,sql,new ScalarHandler(),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

}
