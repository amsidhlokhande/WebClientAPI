package com.amsidh.mvc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.amsidh.mvc.model.Employee;

import reactor.core.publisher.Flux;

public class WebClientRestService {
	private static final Logger logger = LoggerFactory.getLogger(WebClientRestService.class);
	private WebClient webClient;
	public WebClientRestService(@Value("${service.rest.url}") String baseUrl) {
		webClient = WebClientProvider.getWebClientBuilder().baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).filter(logRequest()).build();
	}

	public void getAllEmployee() {
		Flux<Employee> employeeFlux = webClient.get().uri("/employees").accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange().block().bodyToFlux(Employee.class);
		List<Employee> employeeList = employeeFlux.collectList().block();
		System.out.println("Get All Employee");
		employeeList.forEach(emp -> {
			System.out.println(emp.getEmployeeId() + ":" + emp.getEmployeeName());
		});
		
	}

	public void updateEmployee() {
		Employee employee = new Employee(1, "Amsidh Babasha Lokhande");
		Flux<Employee> employeeFlux = webClient.patch().uri("/employees/{employeeId}", 1)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(employee)).exchange().block().bodyToFlux(Employee.class);
		List<Employee> employeeList = employeeFlux.collectList().block();
		System.out.println("Update Employee");
		employeeList.forEach(emp -> {
			System.out.println(emp.getEmployeeId() + ":" + emp.getEmployeeName());
		});
	}

	public void saveEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeName("Anjali Lokhande");
		Flux<Employee> employeeFlux = webClient.post().uri("/employees").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(employee)).exchange().block()
				.bodyToFlux(Employee.class);
		List<Employee> employeeList = employeeFlux.collectList().block();
		System.out.println("Save Employee");
		employeeList.forEach(emp -> {
			System.out.println(emp.getEmployeeId() + ":" + emp.getEmployeeName());
		});
	}

	public void deleteEmployee() {

		Flux<Employee> employeeFlux = webClient.delete().uri("/employees/{employeeId}", 4)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange().block().bodyToFlux(Employee.class);
		List<Employee> employeeList = employeeFlux.collectList().block();
		System.out.println("Delete Employee");
		employeeList.forEach(emp -> {
			System.out.println(emp.getEmployeeId() + ":" + emp.getEmployeeName());
		});
	}

	private ExchangeFilterFunction logRequest() {
		return (clientRequest, next) -> {
			logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
			return next.exchange(clientRequest);
		};
	}

}
