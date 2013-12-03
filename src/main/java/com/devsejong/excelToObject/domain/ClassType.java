package com.devsejong.excelToObject.domain;

import static java.lang.Class.*;

public enum ClassType {
    STRING, INTEGER;

    public static ClassType getClassType(Class clazz) {
        String className = clazz.getName();
        ClassType resultClassType = null;
        switch (className) {
            case "java.lang.String":

                resultClassType = ClassType.STRING;
                break;
            case "java.lang.Integer":
                resultClassType = ClassType.STRING;
                break;
            default:
                throw new IllegalArgumentException(className + "에 해당하는 변환식을 찾을 수 없습니다.");
        }

        return resultClassType;

    }
}
