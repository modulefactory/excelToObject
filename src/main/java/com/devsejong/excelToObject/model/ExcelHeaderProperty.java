package com.devsejong.excelToObject.model;

import java.util.List;

/**
 * 엑셀 파일을 업로드시 필요한 설정정보를 저장한다.
 * 향후 시트, 컬럼헤더등의 옵션이 필요할 수 있으므로 컬럼속성리스트를 한번 감싸도록 한다.
 */
public class ExcelHeaderProperty {
    private List<HeaderColumn> columnList;

    public List<HeaderColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<HeaderColumn> columnList) {
        this.columnList = columnList;
    }
}
