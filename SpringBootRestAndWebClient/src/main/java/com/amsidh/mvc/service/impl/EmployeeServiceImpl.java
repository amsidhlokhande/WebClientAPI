package com.amsidh.mvc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.amsidh.mvc.entities.Employee;
import com.amsidh.mvc.repositories.EmployeeRepository;
import com.amsidh.mvc.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		System.out.println("Loading EmployeeServiceImpl!!!!!");
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> saveEmployee(Employee employee) {
		employeeRepository.saveAndFlush(employee);
		return getAllEmployee();
	}

	@Override
	public List<Employee> updateEmployee(Integer employeeId, Employee employee) {
		Optional<Employee> newEmployee = employeeRepository.findById(employeeId).map(oldEmpl -> {
			oldEmpl.setEmployeeId(employee.getEmployeeId());
			oldEmpl.setEmployeeName(employee.getEmployeeName());
			return oldEmpl;
		});
		newEmployee.ifPresent(emp -> employeeRepository.saveAndFlush(emp));
		return getAllEmployee();
	}

	@Override
	public List<Employee> deleteEmployee(Integer employeeId) {
		employeeRepository.deleteById(employeeId);
		return getAllEmployee();
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll(Sort.by(Direction.ASC, "employeeId"));
	}

}
