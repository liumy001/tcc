package com.eric.demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Configuration
@Import({
		DispatcherServletAutoConfiguration.class,
		EmbeddedServletContainerAutoConfiguration.class,
		ErrorMvcAutoConfiguration.class,
		HttpEncodingAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class,
		JacksonAutoConfiguration.class,
		JmxAutoConfiguration.class,
		MultipartAutoConfiguration.class,
		ServerPropertiesAutoConfiguration.class,
		PropertyPlaceholderAutoConfiguration.class,
		ThymeleafAutoConfiguration.class,
		WebMvcAutoConfiguration.class,
		WebSocketAutoConfiguration.class,
})
public class Application {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
}