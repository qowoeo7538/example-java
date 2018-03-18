package org.shaw.compiler.support;

import org.shaw.util.ClassUtils;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JdkCompiler extends AbstractCompiler {

    /** 默认类加载器类名 */
    private final static String APP_CLASSLOADER = "sun.misc.Launcher$AppClassLoader";

    /** 类加载器 */
    private final ClassLoaderImpl classLoader;

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
        /**
         * 1) 是 URLClassLoader 加载器的实例(通过指向目标文件加载类)
         * 2) 不是默认的类加载器
         */
        if (loader instanceof URLClassLoader
                && (!APP_CLASSLOADER.equals(loader.getClass().getName()))) {
            try {
                URLClassLoader urlClassLoader = (URLClassLoader) loader;
                List<File> files = new ArrayList<>();
                // 获取加载资源的路径
                for (URL url : urlClassLoader.getURLs()) {
                    files.add(new File(url.getFile()));
                }
                // 将加载的文件与文件管理器关联
                manager.setLocation(StandardLocation.CLASS_PATH, files);
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        classLoader = AccessController.doPrivileged((PrivilegedAction<ClassLoaderImpl>) () ->
                new ClassLoaderImpl(loader)
        );
    }

    @Override
    protected Class<?> doCompile(String name, String source) throws Throwable {

        return null;
    }

    /**
     * 类加载器实现
     */
    private final class ClassLoaderImpl extends ClassLoader {

        ClassLoaderImpl(final ClassLoader parentClassLoader) {
            super(parentClassLoader);
        }

    }
}
