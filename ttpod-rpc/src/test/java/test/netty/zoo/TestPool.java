package test.netty.zoo;

import com.ttpod.rpc.netty.pool.impl.CuratorGroupManager;
import com.ttpod.rpc.netty.pool.impl.ZkChannelPool;
import com.ttpod.rpc.pool.GroupManager;
import org.junit.Test;

import java.util.Arrays;

/**
 * date: 2014/5/20 17:16
 *
 * @author: yangyang.cong@ttpod.com
 */
public class TestPool {



    static String zooUrl = "192.168.8.12:2181";

    @Test
    public void testPool() throws InterruptedException {


        ZkChannelPool pool = new ZkChannelPool(zooUrl,"solr4/qsongs");

        Thread.sleep(1000L);
    }


    /**
     * [root@ss ~]# iptables -A INPUT -s 192.168.11.53  -p tcp --dport 2181 -j DROP
     * [root@ss ~]# iptables -F
     *
     * @throws InterruptedException
     */
//    @Test  testGroup()
    public static void main(String[] args)throws Exception {

        GroupManager group = new CuratorGroupManager(zooUrl,"TEST");
        group.join("CuratorGroupManager",null);

        Thread.currentThread().join();
    }

}
