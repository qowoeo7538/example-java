module example.core {
    // 依赖模块
    requires component.common;
    requires component.thread;

    // 对外提供模块
    exports org.lucas.example.core.task;
    exports org.lucas.example.core.util;
}