package com.amsidh.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amsidh.mvc.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   
}
