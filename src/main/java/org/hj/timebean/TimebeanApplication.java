package org.hj.timebean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TimebeanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimebeanApplication.class, args);
    }

}
