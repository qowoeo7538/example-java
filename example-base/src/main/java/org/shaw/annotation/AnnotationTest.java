package org.shaw.annotation;

import java.lang.reflect.Field;

/**
 * Created by joy on 17-2-12.
 */
public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        /*User user = new User();
        user.setId("id430");
        user.setUserName("张三,李四,王老五");
        System.out.println(querySQL(user));*/

        Parent subClass = new Parent();
        Class clazz = subClass.getClass();
        InheritedAnnotation annotation = (InheritedAnnotation)clazz.getAnnotation(InheritedAnnotation.class);
        System.out.println(annotation.value());
    }

    public static String querySQL(User user) throws Exception {
        StringBuilder returnData = new StringBuilder();
        returnData.append("SELECT * FOME ");

        Class clazz = user.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) throw new IllegalAccessException("该对象不含Table注解");

        Table tableObj = (Table) clazz.getAnnotation(Table.class);
        String table = tableObj.value();
        returnData.append(table+" WHERE AND 1=1 ");

        Field[] field = clazz.getDeclaredFields();
        for (Field f : field){
            f.setAccessible(true);
            if (f.isAnnotationPresent(Column.class) && f.get(user)!=null){
                Column columnObj = f.getAnnotation(Column.class);
                String column = columnObj.value();
                if (f.get(user).toString().contains(",")){
                    String[] instr = f.get(user).toString().split(",");
                    returnData.append("IN (");
                    for (String str : instr){
                        returnData.append("'"+str+"',");
                    }
                    returnData.deleteCharAt(returnData.length()-1);
                    returnData.append(") ");
                }else {
                    returnData.append("AND "+column+" = \'"+f.get(user)+"\' ");
                }
            }

        }
        return returnData.toString();
    }
}

@InheritedAnnotation("parent")
class Parent{

}

class SubClass extends Parent{

}
