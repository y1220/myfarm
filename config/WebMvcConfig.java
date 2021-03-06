package it.course.myfarm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	// check the class name according to interface definitions
	public void addCorsMappings(CorsRegistry registry) {

		final long MAX_AGE_SECS = 36000;

		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTION", "PATCH").maxAge(MAX_AGE_SECS);

	}

}
