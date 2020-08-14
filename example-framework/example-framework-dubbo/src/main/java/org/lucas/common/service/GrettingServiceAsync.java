package org.lucas.common.service;

import java.util.concurrent.CompletableFuture;

public interface GrettingServiceAsync {

	CompletableFuture<String> sayHello(String name);
}