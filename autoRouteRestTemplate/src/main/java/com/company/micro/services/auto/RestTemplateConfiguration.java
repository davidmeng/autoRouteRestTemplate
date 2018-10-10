package com.company.micro.services.auto;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @LoadBalanced
    @Bean(name = "dockerRestTemplate")
    @ConditionalOnMissingBean(name = "dockerRestTemplate")
    RestTemplate dockerRestTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "domainRestTemplate")
    @ConditionalOnMissingBean(name = "domainRestTemplate")
    RestTemplate domainRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(RouteDockerRule.class)
    RouteDockerRule DefaultAutoRouteDockerHandler() {
        return new DefaultRouteDockerRule();
    }
}
