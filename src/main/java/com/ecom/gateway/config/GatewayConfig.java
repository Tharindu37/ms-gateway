package com.ecom.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-api", r-> r.path("/user-service/**")
                        .uri("http://localhost:8083"))
                .route("product-api", r-> r.path("/product-service/**")
                        .uri("http://localhost:8081"))
                .route("order-payment-service", r-> r.path("/order-payment-service/**")
                        .uri("http://localhost:3000")).build();
    }
}
