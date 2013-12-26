package com.devsejong.excelToObject.exception;

public class CellValueConvertException extends RuntimeException{

	private static final long serialVersionUID = -1082575998236962490L;

	public CellValueConvertException() {

    }

    public CellValueConvertException(String message) {
        super(message);
    }

    public CellValueConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
