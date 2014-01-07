package com.devsejong.excelToObject;

import com.devsejong.excelToObject.domain.ClassType;
import com.devsejong.excelToObject.domain.Column;
import com.devsejong.excelToObject.domain.ExcelProperty;
import com.devsejong.excelToObject.dummy.Address;
import com.devsejong.excelToObject.dummy.NotAnnotatedObject;
import com.devsejong.excelToObject.except.ExcelToObjectException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.fail;

public class ExcelToObjectTest {
    ExcelToObject excelToObject;
    ExcelProperty excelProperty;

    @Before
    public void setup() {
        excelToObject = new ExcelToObject();
        List<Column> addressColumnList = new ArrayList<>();
        addressColumnList.add(new Column("zipcode", "우편번호", ClassType.STRING));
        addressColumnList.add(new Column("address", "주소", ClassType.STRING));
        excelProperty = new ExcelProperty();
        excelProperty.setColumnList(addressColumnList);
    }

    @Test
    public void testGetObjectList() throws Exception {
        List<Address> objList = excelToObject.getXlsObjectList(
                getTestXlsInputStream(), excelProperty, Address.class
        );

        System.out.println(objList.size());
        for (Address obj : objList) {
            System.out.println(obj.toString());
        }
    }

    @Test
    public void getObjectList() throws FileNotFoundException, ClassNotFoundException {
        List<Address> addr = excelToObject.getXlsObjectList(getTestXlsInputStream(), Address.class);
        System.out.println(addr);
    }


    @Test(expected = ExcelToObjectException.class)
    public void testGetObjectList_propIsNull() {
        excelToObject.getXlsObjectList(getTestXlsInputStream(), null, Address.class);
    }

    @Test(expected = ExcelToObjectException.class)
    public void testGetObjectList_classIsNull() {
        excelToObject.getXlsObjectList(getTestXlsInputStream(), excelProperty, null);
    }

    //어노테이션이 들어가지 않은 객체 조회
    @Test(expected = ExcelToObjectException.class)
    public void getObjectList_hasNoAnnotatedClass() {
        excelToObject.getXlsObjectList(getTestXlsInputStream(), NotAnnotatedObject.class);
    }

    private FileInputStream getTestXlsInputStream() {
        try {
            return new FileInputStream(getTestFolderPath() + "test.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }

}
