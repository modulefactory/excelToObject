package com.devsejong.excelToObject.dummy;

public class DummyObject {
    String test;
    int age;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "DummyObject{" +
                "test='" + test + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
