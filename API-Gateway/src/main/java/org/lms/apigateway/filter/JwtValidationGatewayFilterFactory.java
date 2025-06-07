package org.lms.apigateway.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import java.util.Base64;
import java.util.List;
import java.util.Map;


@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationGatewayFilterFactory.class);
    private final LoadBalancerClientFactory loadBalancerClientFactory;
    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(LoadBalancerClientFactory loadBalancerClientFactory, WebClient.Builder webClientBuilder,
                                             @Value(("${auth.service.url}")) String authServiceUrl) {
        this.loadBalancerClientFactory = loadBalancerClientFactory;
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            logger.info("JwtValidationGatewayFilterFactory: apply() method called");

            if (token == null || !token.startsWith("Bearer ")) {
                logger.warn("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .flatMap(claims -> {
                        String userId = (String) claims.get("userId");
                        String collegeId = (String) claims.get("collegeId");
                        String email = (String) claims.get("email");
                        List<String> roles = (List<String>) claims.get("role");

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header(HttpHeaders.AUTHORIZATION,token)
                                .header("userId", userId)
                                .header("collegeId", collegeId)
                                .header("email", email)
                                .header("roles", String.join(",", roles))
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return chain.filter(mutatedExchange);
                    })
                    .onErrorResume(error -> {
                        logger.error("Token validation failed: {}", error.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

}
