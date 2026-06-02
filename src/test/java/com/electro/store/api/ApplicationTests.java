package com.electro.store.api;

import com.electro.store.api.domain.auth.service.UserService;
import com.electro.store.api.domain.auth.web.request.CreateUserRequest;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.CreatePersonRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserService userService;

	@Test
	void createUser(){
		String firstName = "John";
		String lastName = "Doe";
		String dni = "74224732";
		String phone = "+51 987 681 912";
		String sueldo = "3500";
		String username = "root";
		String password = "root";
		CreatePersonRequest requestPerson = new CreatePersonRequest(firstName, lastName, dni, phone);
		CreateEmployeeRequest request = new CreateEmployeeRequest(requestPerson, EmployeePosition.MANAGER, new BigDecimal(sueldo));
		Employee employee = employeeService.create(request);
		CreateUserRequest requestUser = new CreateUserRequest(username, password, employee.getCode());
		userService.create(requestUser);
	}

}
