package org.lucas.example.persistence.mysql.spring.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.persistence.mysql.common.entity.AutoIdCommonEntity;
import org.lucas.example.persistence.mysql.spring.JAPApplicationConfig;
import org.lucas.example.persistence.mysql.spring.dao.JAPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JAPApplicationConfig.class)
public class JAPDemo {

    @Autowired
    private JAPRepository japRepository;

    @Test
    public void testInsert() {
        AutoIdCommonEntity entity = new AutoIdCommonEntity();
        entity.setColumn0("publishName");
        entity.setColumn1("1");
        entity.setColumn2(LocalDateTime.now());
        entity.setColumn3("zhangsan");
        entity.setColumn4("zhangsan");
        japRepository.save(entity);
    }

    /**
     * 自定义查询
     */
    @Test
    public void testFindById() {
        List<AutoIdCommonEntity> entity = japRepository.customFindAll();
        System.out.println(entity);
    }

}
