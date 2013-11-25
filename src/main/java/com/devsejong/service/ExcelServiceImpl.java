package com.devsejong.service;

import com.devsejong.model.ExcelProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ExcelServiceImpl<T> implements ExcelService<T>{

    @Override
    public T convertExcelToModelList(InputStream inputStream, ExcelProperty excelProperty) {
        //쉬운기능 하나하나부터 먼저 해보자.

        return null;
    }
}
