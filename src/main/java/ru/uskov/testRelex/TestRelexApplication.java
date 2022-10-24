package ru.uskov.testRelex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestRelexApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestRelexApplication.class, args);
	}

}
