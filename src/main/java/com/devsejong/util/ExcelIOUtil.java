package com.devsejong.util;

import com.devsejong.exception.ExcelIOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 엑셀을 업로드 하거나 다운로드한다. 엑셀 업로드나 다운로드의 주 기능은 DB에 데이터를 올리거나, DB의 데이터를 내려받는 용도이다.
 * 그 목적에 맞게끔 해당 엑셀 변환기는 0번째의 컬럼을 Map의 키값으로, 1번째 이하 값에는 데이터를 넣어주어 List<String,Object>의 형태로 결과를 반환한다.
 * <p/>
 * 논의사항.
 * 1. inputStream, outputStream은 여기에서 최종적으로 close를 하고 있다. 여기서 처리하는것이 옳은가? 당장 떠오르지는 않지만, inputStream은
 * 여기뿐만아니라 다른 위치에서도 충분히 사용 가능하지 않을까?
 * 2. static으로 구현하는 것이 옳은가?? 스프링을 사용하여 빈으로 등록하여 싱글톤으로 사용하는 것, 그리고 이와 같이 static으로 처리하는 것,
 * 둘 중 어느 것이 효과적인가??
 */
// 엑셀 업로더 다운로더
public class ExcelIOUtil {
    /**
     * 와 딥따 무식하게 짰다.. 이게 사람이 짠 코드뇨??
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> readXls(InputStream inputStream) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int columnNumber;
        int rowNumber;

        List<Map<String, Object>> resultMapList = new ArrayList<>();

        //첫번쨰 컬럼의 값은 결과맵의 키값으로 사용된다.
        List<String> resultMapColumnKey = new ArrayList<>();
        Iterator rows = sheet.rowIterator();
        //첫번째 로우는 무조건 헤더값이라고 생각
        row = (HSSFRow) rows.next();
        Iterator headerCells = row.cellIterator();
        while (headerCells.hasNext()) {
            cell = (HSSFCell) headerCells.next();
            resultMapColumnKey.add(cell.getStringCellValue());
        }

        rowNumber = 0;
        while (rows.hasNext()) {
            columnNumber = 0;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();
                Object cellValue;
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    cellValue = cell.getStringCellValue();
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    cellValue = cell.getNumericCellValue();
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                    cellValue = null;
                } else {
                    String errorMessage ="Cannot Find Type. Column : " + (columnNumber + 1);
                    errorMessage +=" Row : " + (rowNumber + 1);
                    errorMessage += " Type : " + cell.getStringCellValue();

                    throw new ExcelIOException(errorMessage);
                }

                resultMap.put(resultMapColumnKey.get(columnNumber), cellValue);
                columnNumber += 1;
            }

            resultMapList.add(resultMap);
            rowNumber += 1;
        }

        inputStream.close();
        return resultMapList;
    }

    public static List<Map<String, Object>> readXlsx(InputStream inputStream) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        int columnNumber;
        int rowNumber;

        List<Map<String, Object>> resultMapList = new ArrayList<>();

        //첫번쨰 컬럼의 값은 결과맵의 키값으로 사용된다.
        List<String> resultMapColumnKey = new ArrayList<>();
        Iterator rows = sheet.rowIterator();
        //첫번째 로우는 무조건 헤더값이라고 생각
        row = (XSSFRow) rows.next();
        Iterator headerCells = row.cellIterator();
        while (headerCells.hasNext()) {
            cell = (XSSFCell) headerCells.next();
            resultMapColumnKey.add(cell.getStringCellValue());
        }

        rowNumber = 0;
        while (rows.hasNext()) {
            columnNumber = 0;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
                Object cellValue;
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    cellValue = cell.getStringCellValue();
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    cellValue = cell.getNumericCellValue();
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                    cellValue = null;
                } else {
                    String errorMessage ="Cannot Find Type. Column : " + (columnNumber + 1);
                    errorMessage +=" Row : " + (rowNumber + 1);
                    errorMessage += " Type : " + cell.getStringCellValue();

                    throw new ExcelIOException(errorMessage);
                }

                resultMap.put(resultMapColumnKey.get(columnNumber), cellValue);
                columnNumber += 1;
            }

            resultMapList.add(resultMap);
            rowNumber += 1;
        }

        inputStream.close();
        return resultMapList;
    }

    public static void writeXls(OutputStream outputStream) throws IOException {
        String sheetName = "Sheet1";//name of sheet

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < 5; r++) {
            HSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < 5; c++) {
                HSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public static void writeXlsx(OutputStream outputStream) throws IOException {
        String sheetName = "Sheet1";

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < 5; r++) {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < 5; c++) {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        //write this workbook to an Outputstream.
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
