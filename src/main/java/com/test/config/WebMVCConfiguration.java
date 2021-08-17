package com.test.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMVCConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/common/css/**", "/common/js/**", "/admin/**", "/user/**").addResourceLocations(
				"classpath:/static/common/js/", "classpath:/static/common/css/", "classpath:/static/admin/",
				"classpath:/static/user/");
		Path productUploadDir = Paths.get("./book-images");
		String productUploadPath = productUploadDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/book-images/**").addResourceLocations("file:/" + productUploadPath + "/");
	}

}