package com.test.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.test" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
