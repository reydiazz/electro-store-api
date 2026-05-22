package com.electro.store.api;

import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import com.electro.store.api.domain.people.service.CustomerService;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.domain.people.service.PersonService;
import com.electro.store.api.domain.people.web.request.*;
import com.electro.store.api.domain.people.web.response.PersonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private PersonService personService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;

	@Test
	void CreateEmployee() {
		UpdatePersonRequest requestPerson = new UpdatePersonRequest("Francisco","Ortega","57812345","+54 999888777");
		UpdateEmployeeRequest request = new UpdateEmployeeRequest(requestPerson, EmployeePosition.MANAGER,new BigDecimal("100.45"));
		employeeService.update("EMP260522093458LIBDY",request);
	}
	@Test
	void CreateCustomer(){
		UpdatePersonRequest personRequest = new UpdatePersonRequest("Cesar","Uscuvilca","83837367","+54 912837234");
		UpdateCustomerRequest customerRequest = new UpdateCustomerRequest(personRequest,"1234567890");
		customerService.update("CUS2605220938533C152",customerRequest);
	}

}
