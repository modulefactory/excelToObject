package com.devsejong.service;

import com.devsejong.model.ExcelColumn;
import com.devsejong.model.ExcelProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class ExcelServiceImplTest {

    @Autowired
    ExcelServiceImpl<DummyCustomer> excelService;

    ExcelProperty excelProperty;

    InputStream xlsInputStream;
    InputStream xlsxInputStream;

    @Before
    public void setup() throws FileNotFoundException {
        excelProperty = new ExcelProperty();
        excelProperty.setWorkbook("sheet1");
        List<ExcelColumn> excelColumnList = new ArrayList<ExcelColumn>();
        excelColumnList.add(new ExcelColumn("나이", "age"));
        excelColumnList.add(new ExcelColumn("이름", "name"));

        excelProperty.setExcelColumns(excelColumnList);

        //set inputStream
        xlsInputStream = new FileInputStream(getTestFolderPath() + File.separator + "sample.xls");
        xlsInputStream = new FileInputStream(getTestFolderPath() + File.separator + "sample.xlsx");
    }

    @Test
    public void testConvertXlsToModelList() {
        //excelService.convertExcelToModelList(inputStream, excelProperty);
    }

    @Test
    public void testConvertXlsToDomainList_checkInputStreamIsNull(){
        try{
            excelService.convertXlsToDomainList(null, excelProperty);
            fail();
        }catch(Exception e){
            System.out.println("성공");
        }
    }

    @Test
    public void testConvertXlsToDomainList_checkExcelPropertyIsNull(){
        try{
            excelService.convertXlsToDomainList(xlsInputStream, null);
            fail();
        }catch(Exception e){
            System.out.println("성공");
        }
    }





    private String getTestFolderPath(){
        return this.getClass().getResource("/excelIOUtil").getPath() + File.separator;
    }


}

//test xls파일에 들어가 있는 값들...
class DummyCustomer{
    private String name;
    private int number;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }
}
