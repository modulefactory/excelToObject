package com.devsejong.excelToObject;

import com.devsejong.excelToObject.domain.ClassType;
import com.devsejong.excelToObject.domain.Column;
import com.devsejong.excelToObject.domain.ExcelUploadProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelToObject<T> {
    private static Logger logger = LoggerFactory.getLogger(ExcelToObject.class);

    public List<T> getObjectList(InputStream inputStream, ExcelUploadProperty excelUploadProperty, Class<T> clazz) {
        String className = clazz.getName();

        List<T> objectList = new ArrayList<T>();

        //inputStream을 기준으로 데이터를 가져온다.
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(inputStream);

            // 첫번째 시트만 가져온다.
            Sheet sheet = wb.getSheetAt(0);

            // 첫번째 헤더 로우을 기반으로 column속성값을 가져간다.
            Row row = sheet.getRow(0);
            Map<Integer, Column> columnMap = new HashMap<>();
            List<Column> columnList = excelUploadProperty.getColumnList();

            //상단의 컬럼을 순환하면서 셀값과 매칭되는 객체속성을 가져올 수 있도록 한다.
            //복잡도가 크게 높지 않으므로, 단순 for문을 사용하여 진행하도록 한다.(속도 이슈시 개선.)
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                String cellValue = row.getCell(i).getStringCellValue();
                for(Column column : columnList){
                    if(column.getAliasName().equals(cellValue)){
                        //컬럼형식을 가져온다.
                        String classTypeName = PropertyUtils.getPropertyType(clazz, column.getPropertyName()).getName();

                        column.setClassType(classType);

                        break;
                    }
                }

                System.out.println();
            }


            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally{
            try {
                inputStream.close();
            } catch (IOException e) {}
        }


        return objectList;
    }

    public List<T> getObjectList(HSSFSheet sheet, List<Column> columnList, Class<T> className) throws Exception {
        //결과 object list가 저장.
        List<T> objectList = new ArrayList<T>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

            //헤더로우를 기반으로 데이터를 만들어낸다.
            final T obj = this.createObject(className, columnList, sheet.getRow(i));
            objectList.add(obj);
        }
        return objectList;
    }

    private T createObject(Class<T> className, List<Column> columnList, HSSFRow row) throws Exception {
        Class<T> obj = (Class<T>) Class.forName(className.getName());
        T objInstance = obj.newInstance();

        //object가 존재하는지 검사하고 없을경우 에러처리한다.
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

            //System.out.println(cell.getStringCellValue());
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
