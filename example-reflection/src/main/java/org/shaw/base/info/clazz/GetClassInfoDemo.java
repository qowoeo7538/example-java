package org.shaw.base.info.clazz;

import org.shaw.base.info.clazz.impl.GetClassInfo;

/**
 * 通过反射获取类信息
 */
public class GetClassInfoDemo {

    public static void main(String[] args) throws Exception {
        System.out.println(GetClassInfo.GetClassInfoInternal.FQCN);
        GetClassInfo.printClassMethodMessage(new GetClassInfo());
        System.out.println(GetClassInfo.getShortName(GetClassInfoDemo.class));
    }
}
