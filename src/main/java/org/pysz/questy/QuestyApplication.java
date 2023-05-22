package org.pysz.questy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class QuestyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestyApplication.class, args);
    }


    @Value("${env}")
    String env;

    @Bean
    CommandLineRunner checMode() {
        return a -> {
            System.out.println("App running in mode: " + env);
        };
    }
}
