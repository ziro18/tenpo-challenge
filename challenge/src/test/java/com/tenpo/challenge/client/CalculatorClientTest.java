package com.tenpo.challenge.client;

import com.tenpo.challenge.exception.ApiErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CalculatorClientTest {
    public static final Long NUMBER_1 = 5L;
    public static final Long NUMBER_2 = 5L;
    @Autowired
    private CalculatorClient calculatorClient;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    void whenGetSumIsSuccess_thenReturnsLong() {
        when(restTemplate.getForObject(buildUrl(NUMBER_1, NUMBER_2), Long.class))
                .thenReturn(NUMBER_1 + NUMBER_2);

        Long sum = calculatorClient.getSum(NUMBER_1, NUMBER_2);

        assertThat(sum).isEqualTo(NUMBER_1 + NUMBER_2);
    }

    @Test
    void whenGetSumIsFailAndItHasLastResult_thenReturnsLastResult() {
        calculatorClient.setLastResult(10L);
        when(restTemplate.getForObject(buildUrl(NUMBER_1, NUMBER_2), Long.class))
                .thenThrow(new RuntimeException());

        Long sum = calculatorClient.getSum(NUMBER_1, NUMBER_2);

        assertThat(sum).isEqualTo(NUMBER_1 + NUMBER_2);
    }

    @Test
    void whenGetSumIsFailAndItDoesNotLastResult_thenRetryAndThrowsApiErrorException() {
        calculatorClient.setLastResult(null);
        when(restTemplate.getForObject(buildUrl(NUMBER_1, NUMBER_2), Long.class))
                .thenThrow(new RuntimeException());

        assertThatThrownBy(() -> calculatorClient.getSum(NUMBER_1, NUMBER_2))
                .isInstanceOf(ApiErrorException.class)
                .hasMessage("RateLimiter 'calculatorClient' does not permit further calls");
    }

    private String buildUrl(Long number1, Long number2) {
        return calculatorClient.HOST + calculatorClient.URI + "?number1=" + number1 + "&number2=" + number2;
    }
}