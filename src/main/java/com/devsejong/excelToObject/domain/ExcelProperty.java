package com.devsejong.excelToObject.domain;

import java.util.List;

/**
 * 엑셀 파일을 업로드시 필요한 설정정보를 저장한다.
 * 향후 시트, 컬럼헤더등의 옵션이 필요할 수 있으므로 컬럼속성리스트를 한번 감싸도록 한다.
 */
public class ExcelProperty {
    private List<Column> columnList;

    public ExcelProperty() {
    }

    public ExcelProperty(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }
}
