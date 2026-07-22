package com.mannyHelp.web;

import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebApplication.class, args);
	}

}