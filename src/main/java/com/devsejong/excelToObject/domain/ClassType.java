package com.devsejong.excelToObject.domain;

import static java.lang.Class.*;

public enum ClassType {
    STRING, INTEGER;

    public static ClassType getClassType(Class clazz){
        String className = clazz.getName();
        switch (className){
            case  "java.lang.String" :
                return ClassType.STRING;
                break;
            case "java.lang.Integer" :
                return ClassType.INTEGER;
                break;
        }
    }
}
