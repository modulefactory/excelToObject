package com.devsejong.excelToObject.domain;


public class Column {
    private String propertyName;
    private String aliasName;
    private ClassType classType;

    public Column(String propertyName, String aliasName, ClassType classType) {
        this.propertyName = propertyName;
        this.aliasName = aliasName;
        this.classType  = classType;
    }

    public Column() {
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }


    @Override
    public String toString() {
        return "Column{" +
                "propertyName='" + propertyName + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", classType=" + classType +
                '}';
    }
}
