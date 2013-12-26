package com.devsejong.excelToObject.exception;

/**
 * 엑셀 변환 중 에러 발생.
 */
public class ExcelToObjectException extends RuntimeException{
	
	private static final long serialVersionUID = -400845912102520316L;

	public ExcelToObjectException(String message) {
        super(message);
    }
}
