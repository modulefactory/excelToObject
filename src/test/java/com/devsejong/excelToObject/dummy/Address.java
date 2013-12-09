package com.devsejong.excelToObject.dummy;

import com.devsejong.excelToObject.anno.ExcelColumn;
import com.devsejong.excelToObject.anno.ExcelMapping;

@ExcelMapping
public class Address {
    @ExcelColumn("우편번호")
    private String zipcode;
    @ExcelColumn("주소")
    private String address;

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

    @Override
    public String toString() {
        return "Address{" +
                "zipcode='" + zipcode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
