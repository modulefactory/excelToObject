package com.yoe21c.research.switchcase;

public abstract class Employee {
	public abstract boolean isPayday();
	public abstract Money calculatePay();
	public abstract void deliveryPay(Money pay);
	public abstract String getName();
}
