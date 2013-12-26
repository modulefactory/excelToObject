package com.devsejong.excelToObject.model;


public class HeaderColumn {
    private String propertyName;
    private String aliasName;
    private ClassType classType;

    public HeaderColumn(String propertyName, String aliasName) {
        this.propertyName = propertyName;
        this.aliasName = aliasName;
    }

    public HeaderColumn(String propertyName) {
        this.propertyName = propertyName;
    }

    public HeaderColumn() {
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
