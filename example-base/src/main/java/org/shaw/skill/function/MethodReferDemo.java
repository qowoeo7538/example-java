package org.shaw.skill.function;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class MethodReferDemo {
    /**
     * 方法引用
     */
    @Test
    public void methodReferTest() {
        Set<String> knownNames = new HashSet<>();
        knownNames.add("ttt");
        // Predicate<String> isKnown = (String s) -> knownNames.contains(s);
        Predicate<String> isKnown = knownNames::contains;
        Assert.assertTrue(isKnown.test("ttt"));
    }
}
