package org.lucas.example.framework.dubbo.demo.provider;

import org.lucas.example.framework.dubbo.demo.common.service.GrettingReactorProvider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GrettingReactorProviderDemo implements GrettingReactorProvider {

    @Override
    public Mono<String> requestMonoWithMonoArg(Mono<String> m1, Mono<String> m2) {
        return m1.zipWith(m2, (s, s2) -> s + " " + s2);
    }

    @Override
    public Flux<String> requestFluxWithFluxArg(Flux<String> f1, Flux<String> f2) {
        return f1.zipWith(f2, (s, s2) -> s + " " + s2);
    }
}
