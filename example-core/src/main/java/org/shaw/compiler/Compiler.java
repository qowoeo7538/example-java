package org.shaw.compiler;

import org.shaw.core.extension.SPI;

/**
 * @create: 2018-03-04
 * @description:
 */
@SPI("javassist")
public interface Compiler {
    Class<?> compile(String code, ClassLoader classLoader);
}
