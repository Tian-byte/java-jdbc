package com.tianedu.dao_.test;

import com.tianedu.dao_.dao.ActorDAO;
import com.tianedu.dao_.domain.Actor;
import org.junit.Test;

import java.util.List;

/**
 * @author tian
 */
public class TestDAO {
    @Test
    //测试ActorDAO 对 actor 表的crud操作
    public void testActorDAO() {
        ActorDAO actorDAO = new ActorDAO();
        //测试查询语句
        List<Actor> actors
                = actorDAO.queryMulti("select * from actor where id =?", Actor.class, 1);
        System.out.println("查询结果");
        for (Actor actor : actors) {
            System.out.println(actor);
        }
        //查询单行记录
        Actor actor = actorDAO.querySingle("select * from actor where id =?", Actor.class, 1);
        System.out.println(actor);
//查询单行单列
        Object o = actorDAO.queryScalar("select name from actor where id = ?", 1);
        System.out.println("==== 查询单行列值===");
        System.out.println(o);
        //演示dml操作
        int update =
                actorDAO.update("insert into actor values(null,?,?,?,?)", "张无忌", "男", "2000-11-11", "999");
        System.out.println(update > 0 ? "执行成功":"执行没有影响表");
    }
}
