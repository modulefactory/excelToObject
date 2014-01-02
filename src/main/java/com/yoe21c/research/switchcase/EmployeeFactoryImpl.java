package com.yoe21c.research.switchcase;

public class EmployeeFactoryImpl implements EmployeeFactory {

	private static final int COMMISIONED = 0;
	private static final int HOURLY = 1;
	private static final int SALARIED = 3;

	@Override
	public Employee makeEmployee(EmployeeRecord record) {
		switch(record.type) {
			case COMMISIONED:
				return new CommissionedEmployee(record);
			case HOURLY:
				return new HourlyEmployee(record);
			case SALARIED:
				return new SalariedEmployee(record);
		}
		return null;
	}

}
