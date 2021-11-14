package com.example.demo;

import com.example.demo.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private DemoService demoService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testDemoService() {
		Optional<Integer> temperature = demoService.getTemperature("10118", "04", "01");
		System.out.println(temperature.get());
	}
}
