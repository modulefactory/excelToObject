package com.yoe21c.file2object.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddressTest {

	@Test
	public void testName() throws Exception {
		Address address = Address.FromNewAddressRule("영등포구 물랫 3길");
	}
}
