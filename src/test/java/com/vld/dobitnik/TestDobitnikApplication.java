package com.vld.dobitnik;

import org.springframework.boot.SpringApplication;

public class TestDobitnikApplication {

	public static void main(String[] args) {
		SpringApplication.from(DobitnikApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
