package com.devsejong.service;


import com.devsejong.model.ExcelProperty;

import java.io.InputStream;
import java.util.List;

// 엑셀로 업로드..
public interface ExcelService<T> {
    //파일명이 없으므로 inputStream xls을 먼저 검사한 다음 xlsx인지 검사한다.
    public List<T> convertXlsToDomainList(InputStream inputStream, ExcelProperty excelProperty);

    List<T> convertXlsxToDomainList(InputStream inputStream, ExcelProperty excelProperty);
}
