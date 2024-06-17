package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakurba.roomBookingApp.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeMvcTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	//Those tests work based on the preloaded data.sql

	@Test
	void findEmployeeByIdIs200() throws Exception {
		//given
		Employee emp = new Employee(1L, "test@email.mail", "name", "surname");
		String requestBody = objectMapper.writeValueAsString(emp);

		//when and then
		mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isOk());
	}

	@Test
	void findEmployeeByIdIs404() throws Exception {
		Employee emp = new Employee(2L, "test@email.mail", "name", "surname");

		String requestBody = objectMapper.writeValueAsString(emp);

		mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isNotFound()).andDo(print());
	}

//	@Test
//	void listAllEmployees() throws Exception {
//		List<Employee> employeeList = new ArrayList<>();
//		Employee emp1 = new Employee(1L, "test@email.mail", "name", "surname");
//		Employee emp2 = new Employee(2L, "johny@email.mail", "Johny", "Blaze");
//		employeeList.add(emp1);
//		employeeList.add(emp2);
//
//		when(service.getEmployees()).thenReturn(employeeList);
//
//		mockMvc.perform(get("/api//employees").contentType("application/json")).andExpect(status().isOk()).andDo(print());
//	}
}
