package org.pysz.questy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestyApplication.class, args);
    }

}
