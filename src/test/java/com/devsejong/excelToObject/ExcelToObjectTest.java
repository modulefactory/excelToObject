package com.devsejong.excelToObject;

import com.devsejong.excelToObject.domain.Column;
import com.devsejong.excelToObject.domain.ExcelMapping;
import com.devsejong.excelToObject.dummy.Address;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelToObjectTest {
    @Test
    public void testGetAddressList() throws Exception {
        ExcelToObject<Address> dummyObjectExcelToObject = new ExcelToObject<>();

        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column("zipcode", "우편번호"));
        columnList.add(new Column("address", "주소"));

        ExcelMapping prop = new ExcelMapping();
        prop.setColumnList(columnList);

        List<Address> objList = dummyObjectExcelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), prop, Address.class);

        System.out.println(objList.size());
        for(Address obj : objList){
            System.out.println(obj.toString());
        }
    }

    private String getTestFolderPath(){
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }
}
