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
    public void setup() {
        excelToObject = new AnnotationConfigExcelToObject<>();
        excelToObjectNotAnnotated = new AnnotationConfigExcelToObject<>();
    }

    @Test
    public void getObjectList() throws FileNotFoundException, ClassNotFoundException {
        excelToObject.getObjectList(new FileInputStream(getTestFolderPath() + "test.xls"), Address.class);
    }


    //어노테이션이 들어가지 않은 객체 조회
    @Test
    public void getObjectList_hasNoAnnotatedClass() {
        try {
            excelToObjectNotAnnotated.getObjectList(
                    new FileInputStream(getTestFolderPath() + "test.xls"), NotAnnotatedObject.class
            );
            //어노테이션이 없으므로 에러처리가 되어야지 정상이다.
            fail();
        } catch (FileNotFoundException e) {
            //성공!!^___^
        }
    }

    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "testExcelFile").getPath() + File.separator;
    }

}

//어노테이션이 없는 객체
class NotAnnotatedObject {

}
