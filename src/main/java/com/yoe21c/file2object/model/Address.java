package com.yoe21c.file2object.model;


public class Address implements Model {
	
    private String zipcode;
    
    private String address;
    
    public Address() { }
    
    public Address(String zipcode, String address) {
		super();
		this.zipcode = zipcode;
		this.address = address;
	}
    
    public Address(String newAddress) {
    	this.address = newAddress;
    }
    
    // 생성자를 중복해 정의할 때는(overload) 정적 팩토리 메소드를 사용한다.
    // 라는 클린코드의 가르침을 연습해본다.
    public static Address FromNewAddressRule(String newAddress) {
    	return new Address(newAddress);
    }

	public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
