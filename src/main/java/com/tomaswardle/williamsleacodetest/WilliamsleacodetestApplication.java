package com.tomaswardle.williamsleacodetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WilliamsleacodetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WilliamsleacodetestApplication.class, args);
	}

}
