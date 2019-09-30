package org.lucas.example.framework.web.spring.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: shaw
 * @Date: 2019/6/25 17:21
 */
@Component
public class ServerFactories {

    private final Map<Class<?>, IServer> serverMap = new HashMap<>();

    public <T extends IServer> T getServer(Class<T> typeName) {
        return (T) serverMap.get(typeName);
    }

    @Autowired
    public void register(List<IServer> servers) {
        for (IServer server : servers) {
            serverMap.put(server.getUUID(), server);
        }
    }

}
