package com.devsejong.excelToObject;

import com.devsejong.excelToObject.dummy.Address;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static junit.framework.Assert.fail;

public class AnnotationConfigExcelToObjectTest {
    AnnotationConfigExcelToObject<Address> excelToObject;
    AnnotationConfigExcelToObject<NotAnnotatedObject> excelToObjectNotAnnotated;

    @Before
    public void setup(){
        excelToObject = new AnnotationConfigExcelToObject<>();
        excelToObjectNotAnnotated = new AnnotationConfigExcelToObject<>();
    }

    @Test
    public void getObjectList() throws FileNotFoundException {
        excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), Address.class);
    }


    //옳바르게 데이터가 들어가 있지 않은 경우
    @Test
    public void getObjectList_hasNoAnnotatedClass() throws FileNotFoundException {
        try {
            excelToObjectNotAnnotated.getObjectList(
                    new FileInputStream(getTestFolderPath() + "test.xls"), NotAnnotatedObject.class
            );

            //어노테이션이 없으므로 에러처리가 되어야지 정상이다.
            fail();
        } catch (FileNotFoundException e) {
            //성공!!
        }
    }


    private String getTestFolderPath(){
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }


}

//어노테이션이 없는 객체
class NotAnnotatedObject {

}
