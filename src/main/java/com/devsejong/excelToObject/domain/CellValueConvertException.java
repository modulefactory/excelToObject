package com.devsejong.excelToObject.domain;

public class CellValueConvertException extends RuntimeException{

    public CellValueConvertException() {

    }

    public CellValueConvertException(String message) {
        super(message);
    }

    public CellValueConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
