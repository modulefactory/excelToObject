package com.devsejong.excelToObject.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    //컬럼값과 매칭되는 데이터를 가져옵니다.
    String value();
}
