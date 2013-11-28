package com.devsejong.excelToObject;

import com.devsejong.excelToObject.domain.ClassType;
import com.devsejong.excelToObject.domain.Column;
import com.devsejong.excelToObject.dummy.Address;
import com.devsejong.excelToObject.dummy.DummyObject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelToObjectTest {
    @Test
    @Ignore
    public void testGetDomainList() throws Exception {
        ExcelToObject<DummyObject> dummyObjectExcelToObject = new ExcelToObject<DummyObject>();
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(getTestFolderPath() + "sample.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);
        List<Column> columnList = new ArrayList<Column>();
        columnList.add(new Column("test", "이름", ClassType.STRING));
        columnList.add(new Column("age", "나이", ClassType.STRING));

        List<DummyObject> objList = dummyObjectExcelToObject.getObjectList(sheet, DummyObject.class, columnList);

        System.out.println(objList.size());
        for(DummyObject obj : objList){
            System.out.println(obj.toString());
        }
    }

    @Test
    public void testGetAddressList() throws Exception {
        ExcelToObject<Address> dummyObjectExcelToObject = new ExcelToObject<Address>();
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(getTestFolderPath() + "test.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);

        List<Column> columnList = new ArrayList<Column>();
        columnList.add(new Column("zipcode", "우편번호", ClassType.STRING));
        columnList.add(new Column("address", "주소", ClassType.STRING));


        List<Address> objList = dummyObjectExcelToObject.getObjectList(sheet, Address.class, columnList);

        System.out.println(objList.size());
        for(Address obj : objList){
            System.out.println(obj.toString());
        }
    }


    private String getTestFolderPath(){
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }
}
