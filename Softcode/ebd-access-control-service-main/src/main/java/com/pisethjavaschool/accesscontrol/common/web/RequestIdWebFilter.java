package com.pisethjavaschool.accesscontrol.common.web;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestIdWebFilter implements WebFilter {

    public static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestId = request.getHeaders().getFirst(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        exchange.getResponse().getHeaders().set(REQUEST_ID_HEADER, requestId);
        String finalRequestId = requestId;

        return chain.filter(exchange)
                .doFirst(() -> MDC.put(REQUEST_ID_HEADER, finalRequestId))
                .doFinally(signalType -> MDC.remove(REQUEST_ID_HEADER));
    }
}
