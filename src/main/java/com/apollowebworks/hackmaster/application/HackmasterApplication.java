package com.apollowebworks.hackmaster.application;

import com.apollowebworks.hackmaster.HackmasterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class HackmasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackmasterConfig.class, args);
	}

//	@Override
//	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
//		return application.sources(HackmasterConfig.class);
//	}
}
