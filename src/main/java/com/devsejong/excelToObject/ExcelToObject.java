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
     * <b>리팩토링 필요</b>
     *
     * @param inputStream
     * @param excelProperty
     * @param clazz
     * @return
     */
    public <T> List<T> getObjectList(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        excelToObjectValidate(inputStream, excelProperty, clazz);
        //최종 결과가 여기 저장된다.
        List<T> resultObjectList = new ArrayList<>();

        try {
            // 첫번째 시트만 가져온다.
            Sheet sheet = (new HSSFWorkbook(inputStream)).getSheetAt(0);
            // 첫번째 시트의 헤더 로우을 기반으로 column을 가져온다.
            Row row = sheet.getRow(0);
            Map<Integer, Column> columnMap = getColumnMap(excelProperty, row);

            //만들어진 컬럼 맵을 기반으로 데이터를 가져온다.
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                T t = createObject(sheet.getRow(i), columnMap, clazz);
                resultObjectList.add(t);
            }
            //예외처리는 어떻게 삭제하여야 하는가?? 해당 부분때문에 로직이 지나치게 복잡해 보인다.
        } catch (IOException e) {
            throw new ExcelToObjectException("excelToObject Error.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return resultObjectList;
    }


    /**
     * 어노테이션을 기준으로 데이터를 가져온다.
     * @param inputStream
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getObjectList(InputStream inputStream, Class<T> clazz) {
        //검증과정.
        //@ExcelMapping이 parent class로 있는지 여부를 판단한다.
        if (clazz.isAnnotationPresent(ExcelMapping.class) == false) {
            throw new ExcelToObjectException(clazz.getName() + "은 어노테이션 를 가지고 있지 않습니다.");
        }

        List<Column> columnList = genColumnListBaseOnAnnotation(clazz);

        //어노테이션 값을 기반으로 excelProperty를 가져온다.
        ExcelProperty excelProperty = new ExcelProperty(columnList);
        return getObjectList(inputStream, excelProperty, clazz);
    }

    //어노테이션을 읽어 정상적으로 데이터를 가져올 수 있는지 확인.
    private <T> List<Column> genColumnListBaseOnAnnotation(Class<T> clazz) {
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
        return columnList;
    }

    /**
     * 엑셀 업로드에서 사용될 컬럼 맵을 생성한다.
     *
     * @param excelProperty
     * @param row
     * @return
     */
    private Map<Integer, Column> getColumnMap(ExcelProperty excelProperty, Row row) {
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
        return columnMap;
    }


    /**
     * 어노테이션 값을 기반으로 데이터를 가져온다.
     *
     * @param row
     * @param columnMap
     * @param className
     * @param <T>
     * @return
     */
    private <T> T createObject(Row row, Map<Integer, Column> columnMap, Class<T> className) {
        T objInstance = getObjectInstance(className);

        Iterator<Integer> columnMapIter = columnMap.keySet().iterator();
        while (columnMapIter.hasNext()) {
            int cellIndex = columnMapIter.next();
            Column column = columnMap.get(cellIndex);
            String propertyName = column.getPropertyName();
            logger.debug("작업 대상 셀 : " + cellIndex + "Processing cell propertyName" + propertyName);
            Cell cell = row.getCell(cellIndex);

            //cell값이 존재한다면 아래 구문을 실행한다.
            if (cell != null) {
                Object cellValue = generateObjectBaseOnProperty(column, cell);

                //결과값을 insert 처리.
                try {
                    PropertyUtils.setSimpleProperty(objInstance, propertyName, cellValue);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new ExcelToObjectException("excelToObject Error.", e);
                }
            }

        }
        return objInstance;
    }

    private <T> T getObjectInstance(Class<T> className) {
        Class<T> obj;
        try {
            obj = (Class<T>) Class.forName(className.getName());
        } catch (ClassNotFoundException e) {
            throw new ExcelToObjectException("cannot find class", e);
        }

        T objInstance;
        try {
            objInstance = obj.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelToObjectException("excelToObject Error.", e);
        }
        return objInstance;
    }
    //CellValue를 기반으로 데이터를 가져옵니다.
    private Object generateObjectBaseOnProperty(Column column, Cell cell) {
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
        return cellValue;
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
