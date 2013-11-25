package com.devsejong.model;

import java.util.List;

public class ExcelProperty {
    private String workbook;
    private List<ExcelColumn> excelColumns;

    public String getWorkbook() {
        return workbook;
    }

    public void setWorkbook(String workbook) {
        this.workbook = workbook;
    }

    public List<ExcelColumn> getExcelColumns() {
        return excelColumns;
    }

    public void setExcelColumns(List<ExcelColumn> excelColumns) {
        this.excelColumns = excelColumns;
    }
}
