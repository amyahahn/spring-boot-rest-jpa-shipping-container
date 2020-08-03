package com.amyhahn.ShippingContainerApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.amyhahn.ShippingContainerApplication.controller")
public class ShippingContainerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingContainerApplication.class, args);
	}

}
