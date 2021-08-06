package org.lucas.example.framework.spring.demo.scanner;

import org.lucas.example.framework.spring.demo.scanner.support.ChannelTopic;
import org.lucas.example.framework.spring.demo.scanner.support.EventType;
import org.lucas.example.framework.spring.demo.scanner.support.PatternTopic;
import org.lucas.example.framework.spring.demo.scanner.support.RedisEventListener;
import org.lucas.example.framework.spring.demo.scanner.support.Topic;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RedisListenerClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private static final String VALUE_FIELD = "keys";

    private static final String DATABASE_FIELD = "database";

    private static final String EVENT_TYPE_FIELD = "eventType";

    private static final String GROUP_FIELD = "group";

    private static final String KEY_SPACE_FORMAT = "__keyspace@%s__:%s";

    public RedisListenerClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public Map<String, ManagedMap<RuntimeBeanReference, ManagedSet<Topic>>> doScanAndGet(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        Map<String, ManagedMap<RuntimeBeanReference, ManagedSet<Topic>>> returnData = new HashMap<>(16);
        ScannedGenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (ScannedGenericBeanDefinition) holder.getBeanDefinition();
            AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(definition.getMetadata()
                    .getAnnotationAttributes(RedisEventListener.class.getName()));
            if (Objects.nonNull(annotationAttrs)) {
                String group = annotationAttrs.getString(GROUP_FIELD);
                ManagedMap<RuntimeBeanReference, ManagedSet<Topic>> groupBeanMap = returnData.computeIfAbsent(group, k -> new ManagedMap<>());
                RuntimeBeanReference reference = new RuntimeBeanReference(holder.getBeanName());
                ManagedSet<Topic> topicSet = groupBeanMap.computeIfAbsent(reference, k -> new ManagedSet<>());
                String[] keys = annotationAttrs.getStringArray(VALUE_FIELD);
                int database = annotationAttrs.getNumber(DATABASE_FIELD);
                for (String key : keys) {
                    if (database < 0 || key.contains("*")) {
                        topicSet.add(new PatternTopic(String.format(KEY_SPACE_FORMAT, database < 0 ? "*" : database, key)));
                    } else {
                        topicSet.add(new ChannelTopic(String.format(KEY_SPACE_FORMAT, database, key)));
                    }
                }
                EventType[] eventTypes = (EventType[]) annotationAttrs.get(EVENT_TYPE_FIELD);
                if (Objects.nonNull(eventTypes)) {
                    for (EventType eventType : eventTypes) {
                        topicSet.add(eventType.generateTopic(database));
                    }
                }
            }
        }
        return returnData;
    }

}
