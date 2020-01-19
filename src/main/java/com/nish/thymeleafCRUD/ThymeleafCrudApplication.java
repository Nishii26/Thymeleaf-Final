package com.nish.thymeleafCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.nish.thymeleafCRUD.controller.EmployeeController;

@SpringBootApplication
@ComponentScan(basePackageClasses = EmployeeController.class)
public class ThymeleafCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafCrudApplication.class, args);
	}

}
