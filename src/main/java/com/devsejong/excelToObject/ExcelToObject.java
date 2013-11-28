package com.devsejong.excelToObject;

import com.devsejong.excelToObject.domain.ClassType;
import com.devsejong.excelToObject.domain.Column;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExcelToObject<T> {
    private static Logger logger = LoggerFactory.getLogger(ExcelToObject.class);


    public List<T> getObjectList(HSSFSheet sheet, Class<T> className, List<Column> columnList) throws Exception {
        //결과 object list가 저장.
        List<T> objectList = new ArrayList<T>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

            //헤더로우를 기반으로 데이터를 만들어낸다.
            final T obj = this.createObject(className, sheet.getRow(i), columnList);
            objectList.add(obj);
        }
        return objectList;
    }

    private T createObject(Class<T> className, HSSFRow row, List<Column> columnList) throws Exception {
        Class<T> obj = (Class<T>) Class.forName(className.getName());
        T objInstance = obj.newInstance();

        logger.debug("Setting properties for class" + className.getName());
        if (row == null) {
            return null;
        }

        //object 또한 존재하는지 검사하고 없을경우 에러처리한다.
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            logger.debug("Processing cell" + i);
            Column column = columnList.get(i);
            String propertyName = column.getPropertyName();
            logger.debug("Processing cell propertyName" + propertyName);
            HSSFCell cell = row.getCell(i);

            if (cell == null) {
                continue;
            }

            Object value = null;

            if (column.getClassType() == ClassType.STRING) {
                value = cell.getStringCellValue();
            } else if (column.getClassType() == ClassType.INTEGER) {
                value = new Double(cell.getNumericCellValue()).intValue();
            }

            PropertyUtils.setSimpleProperty(objInstance, propertyName, value);
        }
        return objInstance;
    }
}