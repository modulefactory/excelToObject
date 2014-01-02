package com.yoe21c.research.switchcase;

public class SalariedEmployee extends Employee {

	private EmployeeRecord record;
	
	public SalariedEmployee(EmployeeRecord record) {
		this.record = record;
	}
	
	public EmployeeRecord getRecord() {
		return record;
	}
	public void setRecord(EmployeeRecord record) {
		this.record = record;
	}

	@Override
	public boolean isPayday() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Money calculatePay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deliveryPay(Money pay) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String getName() {
		return "나는 SalariedEmployee 이다.";
	}

}
