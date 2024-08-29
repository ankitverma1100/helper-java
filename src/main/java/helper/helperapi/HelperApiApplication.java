package helper.helperapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableMongoRepositories
@EnableJpaRepositories(basePackages = {"helper.helperapi.mysqlRepo"})
public class HelperApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelperApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//		        .allowedOrigins("*") // Add you arigins here ,to bypass all the origins you can put "*".
//		        .allowedMethods("PUT", "DELETE","POST","GET", "OPTION") // add allowed methods here
////		        .allowedHeaders("header1", "header2", "header3")// add allowed headers here
////		        .exposedHeaders("header1", "header2") // add exposed headers here
//		        .allowCredentials(false).maxAge(3600);
				registry.addMapping("/**").allowedOrigins("*");

			}
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry){
				registry.addResourceHandler("/**")
						.addResourceLocations("classpath:/img/");
			}
		};
	}

}
