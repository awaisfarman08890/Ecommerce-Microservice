package com.ecommerce.apigateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {


    @Bean
    public RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(10,21,1);
    }

    @Bean
    KeyResolver hostNameKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f ->f.retry(retryConfig -> retryConfig
                                .setRetries(10)
                                .setMethods(HttpMethod.GET)

                        )
                                        .requestRateLimiter(config -> config
                                                .setRateLimiter(redisRateLimiter())
                                                .setKeyResolver(hostNameKeyResolver()))

                        )
//                        .filters(f -> f.circuitBreaker(config -> config
//                                .setName("ecomBreaker")
//                                .setFallbackUri("forward:/fallback/products")))
//                        .filters(f -> f.rewritePath("/products(?<segment>/?.*)","/api/products${segment}" ))
                        .uri("http://localhost:8081"))
                .route("user-service", r -> r
                        .path("/api/users/**")
//                         .filters(f -> f.rewritePath("/users(?<segment>/?.*)","/api/users${segment}" ))
                        .uri("http://localhost:8082"))
                .route("order-service", r -> r
                        .path("/api/orders/**", "/api/cart/**")
//                         .filters(f -> f.rewritePath("/(?<segment>/?.*)","/api/${segment}" ))
                        .uri("http://localhost:8083"))
                .route("eureka-server", r -> r
                        .path("/api/eureka/main")
//                         .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();

    }
}
