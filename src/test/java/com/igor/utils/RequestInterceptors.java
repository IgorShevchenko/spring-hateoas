package com.igor.utils;

import java.util.List;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;

public final class RequestInterceptors {

    public final static ClientHttpRequestInterceptor JSON_INTERCEPTOR = (request, body, execution) -> {

        HttpHeaders headers = request.getHeaders();

        headers.remove("Accept");
        headers.remove("Content-Type");

        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");

        return execution.execute(request, body);
    };

    public final static ClientHttpRequestInterceptor XML_INTERCEPTOR = (request, body, execution) -> {

        HttpHeaders headers = request.getHeaders();

        headers.remove("Accept");
        headers.remove("Content-Type");

        headers.add("Accept", "application/xml");
        headers.add("Content-Type", "application/xml");

        return execution.execute(request, body);
    };

    public static void autoSetJsonHeaders(TestRestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getRestTemplate().getInterceptors();
        interceptors.add(JSON_INTERCEPTOR);
    }

    public static void autoSetXmlHeaders(TestRestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getRestTemplate().getInterceptors();
        interceptors.add(XML_INTERCEPTOR);
    }

}
