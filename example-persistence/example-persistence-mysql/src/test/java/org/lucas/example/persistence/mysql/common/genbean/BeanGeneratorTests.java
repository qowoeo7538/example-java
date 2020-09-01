package org.lucas.example.persistence.mysql.common.genbean;

import org.eclipse.collections.api.factory.Lists;
import org.junit.Test;

public class BeanGeneratorTests {

    @Test
    public void testGenerateBean() {
        Property property = new Property();
        property.setComment("测试aaaaaassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        property.setName("aaa");
        property.setType("String");

        System.out.println(BeanGenerator.builder()
                .pack("org.lucas.example.persistence.mysql.common.genbean")
                .name("sss")
                .comment("sfsdfsfffffffffffffffffffffffaaaaaaaa")
                .properties(Lists.mutable.of(property))
                .build());
    }

}
