package com.amsidh.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.amsidh.mvc.service.WebClientRestService;
@SpringBootApplication
public class WebClientRestApi implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication springApplication = 
                new SpringApplicationBuilder()
                .sources(WebClientRestApi.class)
                .web(WebApplicationType.NONE)
                .build();
		springApplication.run(args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String http= "http://localhost:8484/rest";
		String https = "https://localhost:8443/rest";
		WebClientRestService webClientRestService = new WebClientRestService(https);
		webClientRestService.getAllEmployee();
		// webClientRestService.updateEmployee();
		// webClientRestService.saveEmployee();
		// webClientRestService.deleteEmployee();
		
	}

}
