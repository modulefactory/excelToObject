package com.devsejong.excelToObject.except;

/**
 * 엑셀 변환 중 에러 발생.
 */
public class ExcelToObjectException extends RuntimeException{
    public ExcelToObjectException(String message) {
        super(message);
    }

    public ExcelToObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
