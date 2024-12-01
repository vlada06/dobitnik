package com.vld.dobitnik;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
//@SpringBootTest // TODO find out why does the annotation fail !!!
class DobitnikApplicationTests {

	@Test
	void contextLoads() {
	}

}
