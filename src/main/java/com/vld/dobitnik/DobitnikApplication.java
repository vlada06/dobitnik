package com.vld.dobitnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 * <font color="green">
 * <b>
 * Main entry point (Tomcat server) for the Spring Boot application
 * </b>
 * </font>
 * </p>
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DobitnikApplication {

    public static void main(String[] args) {
        SpringApplication.run(DobitnikApplication.class, args);
    }

}
