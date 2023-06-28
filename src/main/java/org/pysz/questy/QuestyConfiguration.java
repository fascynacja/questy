package org.pysz.questy;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.service.QuestionPoller;
import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class QuestyConfiguration {


    @Value("${env}")
    String env;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }

    @PostConstruct
    public void env() {
        log.info("Profile is: " + env);
    }
}
