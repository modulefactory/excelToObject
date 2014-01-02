package com.yoe21c.research.switchcase;

public class CommissionedEmployee extends Employee {

	private EmployeeRecord r;
	
	public CommissionedEmployee(EmployeeRecord r) {
		this.r = r;
	}
	
	public EmployeeRecord getR() {
		return r;
	}
	public void setR(EmployeeRecord r) {
		this.r = r;
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
		return "나는 CommissionedEmployee 이다.";
	}

}
