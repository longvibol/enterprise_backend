package com.pisethjavaschool.platform.web;

import com.pisethjavaschool.platform.logging.CorrelationId;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Slf4j
@Component
public class RequestIdWebFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest req = exchange.getRequest();
		String id = req.getHeaders().getFirst(CorrelationId.HEADER);
		if (id == null || id.isBlank())
			id = UUID.randomUUID().toString();
		exchange.getResponse().getHeaders().set(CorrelationId.HEADER, id);
		String finalId = id;
		return chain.filter(exchange).doFirst(() -> MDC.put(CorrelationId.HEADER, finalId))
				.doFinally(s -> MDC.remove(CorrelationId.HEADER));
	}
}
