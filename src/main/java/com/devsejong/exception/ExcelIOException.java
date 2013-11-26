package com.devsejong.exception;

public class ExcelIOException extends RuntimeException{
    public ExcelIOException(String message){
        super(message);
    }
    public ExcelIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
