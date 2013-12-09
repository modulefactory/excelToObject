package com.devsejong.excelToObject;


import com.devsejong.excelToObject.anno.ExcelColumn;
import sun.management.MethodInfo;

import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 *  어노테이션을 기반으로 도메인 데이터를 가져올 수 있다.
 */
public class AnnotationConfigExcelToObject<T> extends ExcelToObject<T> {
    public List<T> getObjectList(FileInputStream fileInputStream, Class<T> clazz) {
        //검증과정.
        //@ExcelMapping이 parent class로 있는지 여부를 판단한다.

        //클래스가 초기화되지 않은 상태이므로 초기화를 시킨 뒤 어노테이션이 있는지 판별한다.
        Annotation[] annotations = clazz.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation.annotationType());
        }

        //객체 초기화 필요.
        Field[] fields = clazz.getFields();
        System.out.println(fields.length);
        for(Field field : fields){
            System.out.println(field.getName());
            Annotation[] annotations1 = field.getAnnotations();
            for(Annotation annotation : annotations1){
                System.out.println(annotation.annotationType());
            }
        }




        /*
        for (Method method : AnnotationParsing.class
                .getClassLoader()
                .loadClass(("com.devsejong.AnnotationExample"))
                .getMethods()) {
            // checks if MethodInfo annotation is present for the method
            if (method.isAnnotationPresent(com.devsejong.MethodInfo.class)) {
                try {
                    // iterates all the annotations available in the method
                    for (Annotation anno : method.getDeclaredAnnotations()) {
                        System.out.println("Annotation in Method '"
                                + method + "' : " + anno);
                    }
                    MethodInfo methodAnno = method
                            .getAnnotation(MethodInfo.class);
                    if (methodAnno.revision() == 1) {
                        System.out.println("Method with revision no 1 = "
                                + method);
                    }

                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
          */

        return null;
    }
}
