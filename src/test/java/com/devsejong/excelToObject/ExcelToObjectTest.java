package com.devsejong.excelToObject;

import com.devsejong.excelToObject.dummy.Address;
import com.devsejong.excelToObject.exception.ExcelToObjectException;
import com.devsejong.excelToObject.model.ExcelHeaderProperty;
import com.devsejong.excelToObject.model.HeaderColumn;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.fail;

public class ExcelToObjectTest {
    ExcelToObject<Address> dummyObjectExcelToObject;
    ExcelHeaderProperty excelProperty;

    @Before
    public void setup() {
        dummyObjectExcelToObject = new ExcelToObject<>();

        excelProperty = new ExcelHeaderProperty();

        List<HeaderColumn> addressColumnList = new ArrayList<>();
        addressColumnList.add(new HeaderColumn("zipcode", "우편번호"));
        addressColumnList.add(new HeaderColumn("address", "주소"));
        excelProperty.setColumnList(addressColumnList);
    }

    @Test
    public void testGetObjectList() throws Exception {
        List<Address> objList = dummyObjectExcelToObject.getObjectList(
                new FileInputStream(getTestFolderPath() + "test.xls"), excelProperty, Address.class
        );

        System.out.println(objList.size());
        for (Address obj : objList) {
            System.out.println(obj.toString());
        }
    }

    @Test
    public void testGetObjectList_inputStreamIsNull() {
        try {
            dummyObjectExcelToObject.getObjectList(null, excelProperty, Address.class);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    @Test
    public void testGetObjectList_propIsNull() throws FileNotFoundException {
        try {
            dummyObjectExcelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), null, Address.class);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    @Test
    public void testGetObjectList_classIsNull() throws FileNotFoundException {
        try {
            dummyObjectExcelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), excelProperty, null);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }
}
