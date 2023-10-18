package com;

/**
 * @author tian
 */
public class MysqlJdbcImpl implements JdbcInterface {

    @Override
    public Object getConnection() {
        System.out.println("得到MySQL的连接");
        return null;
    }

    @Override
    public void crud() {
        System.out.println("增删修改查");
    }

    @Override
    public void close() {
        System.out.println("关闭 mysql 的连接");
    }
}
