package com.devsejong.excelToObject;

import com.devsejong.excelToObject.dummy.Address;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnnotationConfigExcelToObjectTest {
    AnnotationConfigExcelToObject<Address> excelToObject;
    AnnotationConfigExcelToObject<NotAnnotatedObject> excelToObjectNotAnnotated;

    @Before
    public void setup(){
        excelToObject = new AnnotationConfigExcelToObject<>();
    }

    @Test
    public void getObjectList() throws FileNotFoundException {
        excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), Address.class);
    }


    //옳바르게 데이터가 들어가 있지 않은 경우
    @Test
    public void getObjectList_hasNoAnnotatedClass() throws FileNotFoundException {
        excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), Address.class);
    }



    private String getTestFolderPath(){
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }


}

//어노테이션이 없는 객체
class NotAnnotatedObject {

}
