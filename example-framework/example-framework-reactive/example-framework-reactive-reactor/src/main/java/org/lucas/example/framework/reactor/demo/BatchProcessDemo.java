package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * 分批处理
 * window
 * buffer
 * group
 */
public class BatchProcessDemo {

    @Test
    public void demoWindow(){
        Flux.just(1,2,3,4)
                .window()
    }
}
