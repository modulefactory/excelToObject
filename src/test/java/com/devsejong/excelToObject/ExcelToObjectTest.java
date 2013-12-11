package com.devsejong.excelToObject;

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

        excelProperty = new ExcelProperty();

        List<Column> addressColumnList = new ArrayList<>();
        addressColumnList.add(new Column("zipcode", "우편번호"));
        addressColumnList.add(new Column("address", "주소"));
        excelProperty.setColumnList(addressColumnList);
    }

    @Test
    public void testGetObjectList() throws Exception {
        List<Address> objList = excelToObject.getObjectList(
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
            excelToObject.getObjectList(null, excelProperty, Address.class);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    @Test
    public void testGetObjectList_propIsNull() throws FileNotFoundException {
        try {
            excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), null, Address.class);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    @Test
    public void testGetObjectList_classIsNull() throws FileNotFoundException {
        try {
            excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), excelProperty, null);
            fail();
        } catch (ExcelToObjectException e) {
            //성공!
        }

    }

    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }

    @Test
    public void getObjectList() throws FileNotFoundException, ClassNotFoundException {
        excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), Address.class);
    }


    //어노테이션이 들어가지 않은 객체 조회
    @Test
    public void getObjectList_hasNoAnnotatedClass() {
        try {
            excelToObject.getObjectList(
                    new FileInputStream(getTestFolderPath() + "test.xls"), NotAnnotatedObject.class
            );
            //어노테이션이 없으므로 에러처리가 되어야지 정상이다.
            fail();
        } catch (ExcelToObjectException e) {
            //성공!!^___^
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
