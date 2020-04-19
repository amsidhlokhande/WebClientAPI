package com.amsidh.mvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.amsidh.mvc.entities.Employee;
import com.amsidh.mvc.service.EmployeeService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rest")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		System.out.println("Loading EmployeeController!!!!!");
		this.employeeService = employeeService;
	}

	@PostMapping(value = {
			"/employees" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Employee>> saveEmployee(@RequestBody Employee employee) {
		List<Employee> employees = employeeService.saveEmployee(employee);
		Flux<Employee> employeeFlux = Flux.fromIterable(employees);
		HttpStatus status = employeeFlux != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<Flux<Employee>>(employeeFlux, status);
	}

	@GetMapping(value = { "/employees" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Employee>> getAllEmployee() {
		Flux<Employee> employeeFlux = Flux.fromIterable(employeeService.getAllEmployee());
		HttpStatus status = employeeFlux != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<Flux<Employee>>(employeeFlux, status);
	}

	@PatchMapping(value = {
			"/employees/{employeeId}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Employee>> updateEmployee(@PathVariable Integer employeeId,
			@RequestBody Employee employee) {
		List<Employee> employees = employeeService.updateEmployee(employeeId, employee);
		Flux<Employee> employeeFlux = Flux.fromIterable(employees);
		HttpStatus status = employeeFlux != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<Flux<Employee>>(employeeFlux, status);
	}

	@DeleteMapping(value = {
			"/employees/{employeeId}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Employee>> updateEmployee(@PathVariable Integer employeeId) {
		List<Employee> employees = employeeService.deleteEmployee(employeeId);
		Flux<Employee> employeeFlux = Flux.fromIterable(employees);
		HttpStatus status = employeeFlux != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<Flux<Employee>>(employeeFlux, status);
	}

	@ExceptionHandler(WebClientResponseException.class)
	public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
		logger.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(),
				ex);
		return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
	}

}
