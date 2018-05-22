package com.apollowebworks.hackmaster.application;

import com.apollowebworks.hackmaster.HackmasterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
public class HackmasterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HackmasterConfig.class, args);
	}

	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(HackmasterConfig.class);
	}
}
