package org.shaw.reflection.clazz;

import org.shaw.reflection.clazz.impl.GetClassInfo;

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
