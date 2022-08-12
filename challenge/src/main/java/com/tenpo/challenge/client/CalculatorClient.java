package com.tenpo.challenge.client;

import com.tenpo.challenge.exception.ApiErrorException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CalculatorClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorClient.class);
    public static final String CALCULATOR_CLIENT = "calculatorClient";
    public static final String HOST  = "http://host.docker.internal:8081";
    public static final String URI = "/api/percentage";
    @Autowired
    private RestTemplate restTemplate;

    private Long lastResult;

    @RateLimiter(name = CALCULATOR_CLIENT, fallbackMethod = "getSumOnFail")
    @Retry(name = CALCULATOR_CLIENT, fallbackMethod = "getSumOnFail")
    public Long getSum(Long number1, Long number2) {
        LOGGER.info("call api --> {}" , buildUrl(number1, number2));
        lastResult = restTemplate.getForObject(buildUrl(number1, number2), Long.class);
        return lastResult;
    }

    public Long getSumOnFail(Long number1, Long number2, Exception e) {
        LOGGER.info("fail to call api --> {}" , buildUrl(number1, number2));
        if (lastResult != null) {
            return lastResult;
        }

        throw new ApiErrorException(e.getMessage());
    }

    public void setLastResult(Long lastResult) {
        this.lastResult = lastResult;
    }

    private String buildUrl(Long number1, Long number2) {
        return HOST + URI + "?number1=" + number1 + "&number2=" + number2;
    }
}
