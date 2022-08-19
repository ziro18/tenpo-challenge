package com.tenpo.challenge.client;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class CalculatorClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorClient.class);
    public static final String CALCULATOR_CLIENT = "calculatorClient";
    public static String HOST  = "http://host.docker.internal:8081";
    public static final String URI = "/api/percentage";
    @Autowired
    private RestTemplate restTemplate;

    @RateLimiter(name = CALCULATOR_CLIENT, fallbackMethod = "getSumOnFail")
    @Retry(name = CALCULATOR_CLIENT, fallbackMethod = "getSumOnFail")
    public Optional<Long> getSum(Long number1, Long number2) {
        LOGGER.info("call api --> {}" , buildUrl(number1, number2));
        return Optional.of(restTemplate.getForObject(buildUrl(number1, number2), Long.class));
    }

    public Optional<Object> getSumOnFail(Long number1, Long number2, Exception e) {
        LOGGER.info("fail to call api --> {}" , buildUrl(number1, number2));
        return Optional.empty();
    }

    private String buildUrl(Long number1, Long number2) {
        return HOST + URI + "?number1=" + number1 + "&number2=" + number2;
    }
}
