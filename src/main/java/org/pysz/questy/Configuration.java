package org.pysz.questy;

import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionPoller;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${question.ids}")
    List<String> questionIds;

    @Bean
    QuestionPoller questionTasks(QuestionService questionService, QuestionTraceService questionTraceService) {
        return new QuestionPoller(questionService, questionTraceService, questionIds);
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }

}
