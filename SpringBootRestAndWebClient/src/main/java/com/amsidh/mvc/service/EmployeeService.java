package com.amsidh.mvc.service;

import java.util.List;

import com.amsidh.mvc.entities.Employee;

public interface EmployeeService {

	public List<Employee> saveEmployee(Employee employee);

	public List<Employee> updateEmployee(Integer employeeId, Employee employee);

	public List<Employee> deleteEmployee(Integer employeeId);

	public List<Employee> getAllEmployee();
}
