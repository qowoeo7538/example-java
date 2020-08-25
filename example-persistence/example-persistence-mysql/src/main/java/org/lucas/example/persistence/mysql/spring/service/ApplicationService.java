package org.lucas.example.persistence.mysql.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.persistence.mysql.spring.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class ApplicationService {

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void testFindOne(){
        jdbc.queryForObject()
    }

}
