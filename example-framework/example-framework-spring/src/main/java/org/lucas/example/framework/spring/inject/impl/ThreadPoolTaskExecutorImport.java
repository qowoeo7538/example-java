package org.lucas.example.framework.spring.inject.impl;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolTaskExecutorImport implements ImportSelector {

    /**
     * @param importingClassMetadata 表示当前被 @Import 注解给标注的所有注解信息
     * @return 需要导入到容器中的组件全类名(不可返回 null ， 否则空指针异常)
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ThreadPoolTaskExecutor.class.getName()};
    }

}
