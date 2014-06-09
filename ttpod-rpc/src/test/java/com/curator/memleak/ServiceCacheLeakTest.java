package com.curator.memleak;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.junit.Test;

public class ServiceCacheLeakTest {

    @Test
    public void serviceCacheInstancesLeaked() throws Exception {

        final CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("zookeeper1:2181,zookeeper2:2181,zookeeper3:2181", new RetryOneTime(1));
        curatorFramework.start();

        final ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class).client(curatorFramework.usingNamespace("dev")).basePath("/instances").build();
        serviceDiscovery.start();

        for (int i = 0; i < 100000; i++) {
            final ServiceProvider<Void> s = serviceProvider(serviceDiscovery, "myservice");
            s.start();
            try {
                s.getInstance().buildUriSpec();
            } finally {
                s.close();
            }
        }

    }

    private ServiceProvider<Void> serviceProvider(ServiceDiscovery<Void> serviceDiscovery, String name) throws Exception {
        return serviceDiscovery.serviceProviderBuilder().serviceName(name).providerStrategy(new RandomStrategy<Void>()).build();
    }
}