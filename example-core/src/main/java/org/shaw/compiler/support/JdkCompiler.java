package org.shaw.compiler.support;

import org.shaw.util.ClassUtils;

import javax.tools.*;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JdkCompiler extends AbstractCompiler {

    /** java编译器 */
    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    /** 诊断监听器 */
    private final DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();

    /** 编译相关参数 */
    private volatile List<String> options;

    public JdkCompiler() {
        options = new ArrayList<>();
        options.add("-source");
        options.add("1.6");
        options.add("-target");
        options.add("1.6");
        /**
         * 标准的java文件管理器(java编译器需要)
         * 作用:
         *   1) 用于构建编译器的读写功能 (可能会减少对文件系统的扫描和jar文件读写的开销)
         *   2) 在多个编译任务之间共享
         */
        StandardJavaFileManager manager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        final ClassLoader loader = ClassUtils.getDefaultClassLoader();
        if (loader instanceof URLClassLoader
                // 判断是否默认的类加载器
                && (!loader.getClass().getName().equals("sun.misc.Launcher$AppClassLoader"))) {

        }
    }

    @Override
    protected Class<?> doCompile(String name, String source) throws Throwable {

        return null;
    }
}
