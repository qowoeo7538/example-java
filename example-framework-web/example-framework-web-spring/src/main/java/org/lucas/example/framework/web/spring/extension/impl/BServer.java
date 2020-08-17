package org.lucas.example.framework.web.spring.extension.impl;

import org.lucas.example.framework.web.spring.extension.IServer;
import org.springframework.stereotype.Component;

@Component
public class BServer implements IServer {

    public static final Class<BServer> UUID = BServer.class;

    @Override
    public Class<BServer> getUUID() {
        return UUID;
    }

    public String callB(String url) {
        return "BServer#callB";
    }

}
