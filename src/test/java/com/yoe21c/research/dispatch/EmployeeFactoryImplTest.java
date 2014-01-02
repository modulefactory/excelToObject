package com.yoe21c.research.dispatch;

import org.junit.Test;

import com.yoe21c.research.switchcase.Employee;
import com.yoe21c.research.switchcase.EmployeeFactoryImpl;
import com.yoe21c.research.switchcase.EmployeeRecord;

public class EmployeeFactoryImplTest {

	@Test
	public void testName() throws Exception {
		EmployeeFactoryImpl employeeFactory = new EmployeeFactoryImpl();
		EmployeeRecord record = new EmployeeRecord();
		record.setType(0);
		Employee employee = employeeFactory.makeEmployee(record);
		System.out.println(employee.getName());
	}
}
