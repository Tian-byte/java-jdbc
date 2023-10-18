package com;

/**
 * @author tian
 */
public class OracleJdbcImpl implements JdbcInterface {


    @Override
    public Object getConnection() {
        System.out.println("得到Oracle的连接");
        return null;
    }

    @Override
    public void crud() {
        System.out.println("完成增修改查");
    }

    @Override
    public void close() {
        System.out.println("关闭Oracle的连接");
    }
}
