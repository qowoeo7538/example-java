package org.lucas.kata.function;

import org.junit.Assert;
import org.junit.Test;
import org.lucas.kata.function.impl.Function;
import org.lucas.kata.function.impl.Function2;
import org.lucas.kata.function.impl.FunctionRefer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * <p>
 * 静态方法引用：ClassName::methodName
 * 实例上的实例方法引用：instanceReference::methodName
 * 超类上的实例方法引用：super::methodName
 * 类型上的实例方法引用：ClassName::methodName
 * 构造方法引用：Class::new
 * 数组构造方法引用：TypeName[]::new
 */
public class MethodReferDemo {

    /**
     * 静态方法引用
     * <p>
     * 方法引用条件：
     * 引用方法跟接口方法参数列表和返回类型一致。
     */
    @Test
    public void staticReferTest() {
        Function<Integer, Integer> function = FunctionRefer::add;
        Assert.assertTrue(1 + 2 == function.apply(1, 2));
    }

    /**
     * 实例方法引用
     * <p>
     * 方法引用条件：
     * 引用方法跟接口方法参数列表和返回类型一致。
     */
    @Test
    public void instanceReferenceTest() {
        final Set<String> knownNames = new HashSet<>();
        knownNames.add("ttt");
        /**
         * Predicate<String> isKnown = new Predicate<String>() {
         *   @Override
         *   public boolean test(String s) {
         *     return knownNames.contains(s);
         *   }
         * };
         */
        Predicate<String> isKnown = knownNames::contains;
        Assert.assertTrue(isKnown.test("ttt"));
    }


    /**
     * 类型上的实例方法引用
     * <p>
     * 方法引用条件：
     * 函数接口的参数列表 = ClassName + 参数类型列表
     */
    @Test
    public void typeReferenceTest() {
        {
            /**
             * final java.util.function.Function<FunctionRefer, String> function = new java.util.function.Function() {
             *             @Override
             *             public Object apply(Object o) {
             *                 FunctionRefer function = (FunctionRefer) o;
             *                 return function.messge();
             *             }
             *         };
             */
            final java.util.function.Function<FunctionRefer, String> function = FunctionRefer::messge;
            System.out.println(function.apply(new FunctionRefer()));
        }
        {
            /**
             * final Function2<FunctionRefer, String, Integer, String> function = new Function2<FunctionRefer, String, Integer, String>() {
             *                 @Override
             *                 public String apply(FunctionRefer a, String b, Integer c) {
             *                     return a.concat(b, c);
             *                 }
             *             };
             */
            final Function2<FunctionRefer, String, Integer, String> function = FunctionRefer::concat;
            System.out.println(function.apply(new FunctionRefer(), "A", 1));
        }
    }

}
