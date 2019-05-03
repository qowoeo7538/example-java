package org.lucas.base.info.modifier;

import org.lucas.base.info.modifier.impl.ModifierTests;

import java.lang.reflect.Modifier;

public class ModifierDemo {
    public static void main(String[] args) {
        try {
            System.out.println(Modifier.isPublic(ModifierTests.class.getMethod("testPublicModifier").getModifiers()));
        }catch (NoSuchMethodException ex){
            ex.printStackTrace();
        }
    }
}
