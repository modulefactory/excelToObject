package com.devsejong.excelToObject;


import com.devsejong.excelToObject.annotation.ExcelColumn;
import com.devsejong.excelToObject.annotation.ExcelMapping;
import com.devsejong.excelToObject.exception.ExcelToObjectException;
import com.devsejong.excelToObject.model.ClassType;
import com.devsejong.excelToObject.model.ExcelHeaderProperty;
import com.devsejong.excelToObject.model.HeaderColumn;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *  어노테이션을 기반으로 도메인 데이터를 가져올 수 있다.
 */
public class AnnotationConfigExcelToObject<T> extends ExcelToObject<T> {
    public List<T> getObjectList(InputStream inputStream, Class<T> clazz){
        //검증과정.
        //@ExcelMapping이 parent class로 있는지 여부를 판단한다.
        if(clazz.isAnnotationPresent(ExcelMapping.class) == false){
            throw new ExcelToObjectException(clazz.getName() + "은 어노테이션 를 가지고 있지 않습니다.");
        }

        List<HeaderColumn> columnList = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(ExcelColumn.class)){
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                HeaderColumn column = new HeaderColumn();
                column.setPropertyName(field.getName());
                column.setAliasName(excelColumn.value());
                column.setClassType(ClassType.getClassType(field.getType()));

                columnList.add(column);
            }
        };

        //어노테이션 값을 기반으로 excelProperty를 가져온다.
        ExcelHeaderProperty excelProperty = new ExcelHeaderProperty();
        excelProperty.setColumnList(columnList);

        ExcelToObject<T> excelToObject = new ExcelToObject<>();

        return excelToObject.getObjectList(inputStream, excelProperty, clazz);
    }
}
