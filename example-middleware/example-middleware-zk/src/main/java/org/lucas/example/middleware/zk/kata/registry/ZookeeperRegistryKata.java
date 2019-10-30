package org.lucas.example.middleware.zk.kata.registry;

public class ZookeeperRegistryKata {

    @Test
    public void testAnyHost() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            URL errorUrl = URL.valueOf("multicast://0.0.0.0/");
            new ZookeeperRegistryFactory().createRegistry(errorUrl);
        });
    }
}
