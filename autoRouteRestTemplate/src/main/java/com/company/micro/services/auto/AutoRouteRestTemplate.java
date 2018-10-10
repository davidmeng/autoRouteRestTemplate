package com.company.micro.services.auto;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component("restTemplate")
public class AutoRouteRestTemplate extends RestTemplate {

    @Autowired
    @Qualifier("dockerRestTemplate")
    RestTemplate dockerRestTemplate;

    @Autowired
    @Qualifier("domainRestTemplate")
    RestTemplate domainRestTemplate;

    @Autowired
    RouteDockerRule routeDockerRule;

    @Override
    public <T> T execute(URI url, HttpMethod method, RequestCallback requestCallback,
            ResponseExtractor<T> responseExtractor) throws RestClientException {

        if (routeDockerRule.isDockerHost(url)) {
            return dockerRestTemplate.execute(url, method, requestCallback, responseExtractor);
        } else {
            return domainRestTemplate.execute(url, method, requestCallback, responseExtractor);
        }
    }

    @Override
    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
            ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException {

        URI expanded = getUriTemplateHandler().expand(url, uriVariables);
        return execute(expanded, method, requestCallback, responseExtractor);
    }

    @Override
    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
            ResponseExtractor<T> responseExtractor, Map<String, ?> uriVariables) throws RestClientException {

        URI expanded = getUriTemplateHandler().expand(url, uriVariables);
        return execute(expanded, method, requestCallback, responseExtractor);
    }
}
