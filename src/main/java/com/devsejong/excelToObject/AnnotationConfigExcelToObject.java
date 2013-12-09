package com.devsejong.excelToObject;


import java.io.FileInputStream;
import java.util.List;

/**
 *  어노테이션을 기반으로 도메인 데이터를 가져올 수 있다.
 */
public class AnnotationConfigExcelToObject<T> extends ExcelToObject<T> {
    public List<T> getObjectList(FileInputStream fileInputStream, Class<T> addressClass) {
        return null;
    }
}
