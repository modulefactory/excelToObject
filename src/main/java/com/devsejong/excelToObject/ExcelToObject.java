package com.devsejong.excelToObject;

import com.devsejong.excelToObject.anno.ExcelColumn;
import com.devsejong.excelToObject.anno.ExcelMapping;
import com.devsejong.excelToObject.domain.ClassType;
import com.devsejong.excelToObject.domain.Column;
import com.devsejong.excelToObject.domain.ExcelProperty;
import com.devsejong.excelToObject.except.ExcelToObjectException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 엑셀데이터를 객체로 변환한다.
 * 보통 복수의 데이터를 변환하므로 List형식을 먼저 진행하고, 필요에 따라 메서드를 추가시켜나간다.
 * 여기서 T는 필요없는 구문이다 어떻게 제거해야 하는지 파악한다.
 *
 * @param
 */
public class ExcelToObject {
    private static Logger logger = LoggerFactory.getLogger(ExcelToObject.class);

    /**
     * 엑셀데이터를 기준으로 List값을 반환한다.
     * <p/>
     * <br/></><b>리팩토링 필요</b>
     *
     * @param inputStream
     * @param excelProperty
     * @param clazz
     * @return
     */
    public <T> List<T> getObjectList(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        excelToObjectValidate(inputStream, excelProperty, clazz);

        List<T> objectList = new ArrayList<>();
        Workbook wb = null;

        try {
            wb = new HSSFWorkbook(inputStream);

            // 첫번째 시트만 가져온다.
            Sheet sheet = wb.getSheetAt(0);

            // 첫번째 헤더 로우을 기반으로 column속성값을 가져간다.
            Row row = sheet.getRow(0);
            Map<Integer, Column> columnMap = new HashMap<>();
            List<Column> columnList = excelProperty.getColumnList();

            //상단의 컬럼을 순환하면서 셀값과 매칭되는 객체속성을 가져올 수 있도록 한다.
            //복잡도가 크게 높지 않으므로, 단순 for문을 사용하여 진행하도록 한다
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                String headerCellValue = row.getCell(i).getStringCellValue();
                for (Column column : columnList) {
                    if (column.getAliasName().equals(headerCellValue)) {
                        //컬럼형식을 가져온다.
                        columnMap.put(new Integer(i), column);
                        break;
                    }
                }
            }

            //만들어진 컬럼 맵을 기반으로 데이터를 가져온다.
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                T t = createObject(sheet.getRow(i), columnMap, clazz);
                objectList.add(t);
            }
            //예외처리는 어떻게 삭제하여야 하는가?? 해당 부분때문에 로직이 지나치게 복잡해 보인다.
            //상속을 이용하여 처리한다??
        } catch (InvocationTargetException
                | ClassNotFoundException
                | NoSuchMethodException
                | InstantiationException
                | IOException
                | IllegalAccessException e) {
            throw new ExcelToObjectException("excelToObject Error.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return objectList;
    }

    /**
     * 어노테이션 값을 기반으로 데이터를 가져온다.
     *
     * @param row
     * @param columnMap
     * @param className
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private <T> T createObject(Row row, Map<Integer, Column> columnMap, Class<T> className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Class<T> obj = (Class<T>) Class.forName(className.getName());
        T objInstance = obj.newInstance();

        Iterator<Integer> columnMapIter = columnMap.keySet().iterator();
        while (columnMapIter.hasNext()) {
            int cellIndex = columnMapIter.next();
            Column column = columnMap.get(cellIndex);
            logger.debug("작업 대상 셀 : " + cellIndex);

            String propertyName = column.getPropertyName();
            logger.debug("Processing cell propertyName" + propertyName);
            Cell cell = row.getCell(cellIndex);

            if (cell == null) {
                continue;
            }

            Object cellValue = null;

            //리팩터링 필요...
            if (column.getClassType() == ClassType.STRING) {
                cellValue = cell.getStringCellValue();
            } else if (column.getClassType() == ClassType.INTEGER) {
                cellValue = new Double(cell.getNumericCellValue()).intValue();
            } else if (column.getClassType() == ClassType.LONG) {
                cellValue = new Double(cell.getNumericCellValue()).longValue();
            } else if (column.getClassType() == ClassType.FLOAT) {
                cellValue = new Float(cell.getNumericCellValue()).floatValue();
            } else if (column.getClassType() == ClassType.DOUBLE) {
                cellValue = new Float(cell.getNumericCellValue()).doubleValue();
            }

            //결과값을 insert 처리.
            PropertyUtils.setSimpleProperty(objInstance, propertyName, cellValue);
        }
        return objInstance;
    }

    public <T> List<T> getObjectList(InputStream inputStream, Class<T> clazz) {
        //검증과정.
        //@ExcelMapping이 parent class로 있는지 여부를 판단한다.
        if (clazz.isAnnotationPresent(ExcelMapping.class) == false) {
            throw new ExcelToObjectException(clazz.getName() + "은 어노테이션 를 가지고 있지 않습니다.");
        }

        List<Column> columnList = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                Column column = new Column();
                column.setPropertyName(field.getName());
                column.setAliasName(excelColumn.value());
                column.setClassType(ClassType.getClassType(field.getType()));

                columnList.add(column);
            }
        }

        //어노테이션 값을 기반으로 excelProperty를 가져온다.
        ExcelProperty excelProperty = new ExcelProperty();
        excelProperty.setColumnList(columnList);

        ExcelToObject excelToObject = new ExcelToObject();

        return excelToObject.getObjectList(inputStream, excelProperty, clazz);
    }

    private <T> void excelToObjectValidate(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        if (inputStream == null) {
            throw new ExcelToObjectException("inputStream is null!!");
        } else if (excelProperty == null) {
            throw new ExcelToObjectException("excelProperty is null!!");
        } else if (clazz == null) {
            throw new ExcelToObjectException("class is null!!");
        }
    }
}
