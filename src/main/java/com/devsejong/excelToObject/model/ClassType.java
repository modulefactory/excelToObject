package com.devsejong.excelToObject.model;


public enum ClassType {
    STRING, INTEGER, LONG, FLOAT, DOUBLE, DATE;

    public static ClassType getClassType(Class<?> clazz) {
        String className = clazz.getName();
        ClassType resultClassType = null;
        switch (className) {
            case "java.lang.String":
                resultClassType = ClassType.STRING;
                break;
            case "java.lang.Integer":
                resultClassType = ClassType.INTEGER;
                break;
            case "java.lang.Long":
                resultClassType = ClassType.LONG;
                break;
            case "java.lang.Double":
                resultClassType = ClassType.DOUBLE;
                break;
            case "java.lang.Float":
                resultClassType = ClassType.FLOAT;
                break;
            case "java.util.Date":
                resultClassType = ClassType.DATE;
                break;
            default:
                throw new IllegalArgumentException(className + "에 해당하는 변환식을 찾을 수 없습니다.");
        }

        return resultClassType;

    }
}
