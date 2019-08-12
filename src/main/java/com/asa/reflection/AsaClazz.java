package com.asa.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Description
 * @Date 2019-07-29 11:10
 * @Author Asa
 * @Version 1.0
 **/
public class AsaClazz {

    public static void main(String[] args) throws NoSuchFieldException {
        Class clazz = AsaReflect.class;
        System.out.println(clazz.getName());
        System.out.println(clazz.getCanonicalName());

        // Field
        System.out.println("————————Field————————");
        System.out.println(clazz.getDeclaredField("age").getName());
        System.out.println("$ 获取所有的成员变量");
        Field[] fieldArr = clazz.getDeclaredFields();
        Arrays.asList(fieldArr).forEach(field -> {
            System.out.println(field.getName());
        });
        System.out.println("$ 获取public的成员变量");
        fieldArr = clazz.getFields();
        Arrays.asList(fieldArr).forEach(field -> {
            System.out.println(field.getName());
            System.out.println(field.getModifiers()); // 返回一个用于描述属性的修饰符的整型数值
            System.out.println(field.getType().getName()); // 获取字段的class
            System.out.println(field.getDeclaringClass());
        });
        System.out.println("————————Field————————");

        System.out.println("————————methods————————");
        Method[] methodArr = clazz.getDeclaredMethods();
        Arrays.asList(methodArr).forEach(method -> {
            System.out.println(method.getName());
            System.out.println(method.getReturnType());
            System.out.println(method.getDefaultValue());
            Arrays.asList(method.getParameters()).forEach(parameter -> {System.out.println(parameter.getName());});
        });
        System.out.println("————————methods————————");
    }

}
