package com.devsejong.model;

public class ExcelColumn {
    String excelColumnName;
    String modelPropertyName;

    public ExcelColumn() {
    }

    public ExcelColumn(String excelColumnName, String modelPropertyName) {
        this.excelColumnName = excelColumnName;
        this.modelPropertyName = modelPropertyName;
    }

    public String getExcelColumnName() {
        return excelColumnName;
    }

    public void setExcelColumnName(String excelColumnName) {
        this.excelColumnName = excelColumnName;
    }

    public String getModelPropertyName() {
        return modelPropertyName;
    }

    public void setModelPropertyName(String modelPropertyName) {
        this.modelPropertyName = modelPropertyName;
    }
}
