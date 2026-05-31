package com.electro.store.api;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.model.enums.Role;
import com.electro.store.api.domain.auth.repository.UserRepository;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.CreatePersonRequest;
import com.electro.store.api.domain.people.web.response.EmployeeResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeService employeeService;

	@Test
	void createUser(){

		String firstName = "John";
		String lastName = "Doe";
		String dni = "74224732";
		String phone = "+51 987 681 912";

		String PREFIX_USER = "USR";
		String code = CodeGenerator.next(PREFIX_USER);
		String username = "U23226030";
		String password = "root";
		Role role = Role.ADMIN;

		CreatePersonRequest requestPerson = new CreatePersonRequest(
				firstName,
				lastName,
				dni,
				phone
		);

		CreateEmployeeRequest request = new CreateEmployeeRequest(
				requestPerson,
				EmployeePosition.MANAGER,
				new BigDecimal("3500")
		);

		EmployeeResponse response = employeeService.create(request);
		Employee employee = employeeService.findByCodeOrThrow(response.code());

		String passwordByCrypt = new BCryptPasswordEncoder().encode(password);

		User user = new User(
				code,
				username,
				passwordByCrypt,
				employee
		);

		userRepository.save(user);

	}

}
