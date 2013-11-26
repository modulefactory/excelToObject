package com.devsejong.service;

import com.devsejong.model.ExcelProperty;
import com.devsejong.util.ExcelIOUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ExcelServiceImpl<T> implements ExcelService<T> {

    @Override
    public List<T> convertXlsToDomainList(InputStream inputStream, ExcelProperty excelProperty) {
        //쉬운기능 하나하나부터 먼저 해보자.
        //일단 inputStream이나 excelProperty가 없으면 에러를 발생시킨다.
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream을 찾을 수 없습니다.");
        }else if (excelProperty == null) {
            throw new IllegalArgumentException("excelProperty를 찾을 수 없습니다.");
        }

        return null;
    }

    //엑셀 xlsx를 가져오는건 xls와 유사성을 가질것으로 예상된다.
    //리팩토링과 함께 작업을 진행하도록 하자.
    @Override
    public List<T> convertXlsxToDomainList(InputStream inputStream, ExcelProperty excelProperty){
        throw new RuntimeException("Need Work...");
    }
}
