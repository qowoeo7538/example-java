package org.lucas.example.persistence.mysql.spring.service;

import org.eclipse.collections.api.factory.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.persistence.mysql.spring.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class ApplicationService {

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void testFindOne() {
        RowMapper<Map<String, String>> rowMapper = (ResultSet rs, int rowNum) -> {
            Map<String, String> map = Maps.mutable.with();
            map.put("title", rs.getString("title"));
            return map;
        };
        Map<String, String> map = jdbc.queryForObject("SELECT * FROM t_application WHERE id = ?", rowMapper, 1);
        System.out.println(map);
    }

}
