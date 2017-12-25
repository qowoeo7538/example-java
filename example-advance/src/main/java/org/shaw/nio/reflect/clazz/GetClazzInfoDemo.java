package org.shaw.nio.reflect.clazz;

import org.shaw.reflect.clazz.impl.GetClassInfo;

/**
 * 通过反射获取类信息
 */
public class GetClazzInfoDemo {

    public static void main(String[] args) throws Exception {
        System.out.println(GetClassInfo.GetClassInfoInternal.FQCN);
        GetClassInfo.printClassMethodMessage(new GetClassInfo());
        System.out.println(GetClassInfo.getShortName(GetClazzInfoDemo.class));
    }
}
