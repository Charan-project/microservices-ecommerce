package com.example.orderservice.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class ResilienceConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory>
    defaultCustomizer() {

        return factory -> factory.configureDefault(id ->
                new org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder(id)

                        .timeLimiterConfig(
                                TimeLimiterConfig.custom()
                                        .timeoutDuration(Duration.ofSeconds(2))
                                        .build()
                        )

                        .circuitBreakerConfig(
                                CircuitBreakerConfig.custom()
                                        .slidingWindowSize(5)
                                        .minimumNumberOfCalls(3)
                                        .failureRateThreshold(50)
                                        .waitDurationInOpenState(Duration.ofSeconds(10))
                                        .permittedNumberOfCallsInHalfOpenState(1)
                                        .build()
                        )

                        .build()
        );
    }
}