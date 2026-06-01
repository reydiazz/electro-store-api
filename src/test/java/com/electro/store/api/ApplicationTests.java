package com.electro.store.api;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.model.enums.Role;
import com.electro.store.api.domain.auth.repository.UserRepository;
import com.electro.store.api.domain.buys.repository.SupplierRepository;
import com.electro.store.api.domain.buys.service.SupplierService;
import com.electro.store.api.domain.buys.web.request.CreateSupplierRequest;
import com.electro.store.api.domain.buys.web.request.UpdateSupplierRequest;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
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
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierService supplierService;

	@Test
	void createSupplier() {

		UpdateSupplierRequest request = new UpdateSupplierRequest(
				"20123456789",
				"Tv Perú",
				"999888777",
				"Electro Perú SAC"
		);

		SupplierResponse response = supplierService.update("SUP2606011501116J863",request);
	}

}
