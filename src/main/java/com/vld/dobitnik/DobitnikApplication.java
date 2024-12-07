package com.vld.dobitnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })

public class DobitnikApplication {

	public static void main(String[] args) {
		SpringApplication.run(DobitnikApplication.class, args);
	}

}
