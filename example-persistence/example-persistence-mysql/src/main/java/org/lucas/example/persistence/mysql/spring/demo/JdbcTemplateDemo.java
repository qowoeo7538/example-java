package org.lucas.example.persistence.mysql.spring.demo;

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
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class JdbcTemplateDemo {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 查询所有数据
     */
    @Test
    public void testQuery() {
        // 将结果集中的每行数据映射为一个对象
        RowMapper<Map<String, String>> rowMapper = (ResultSet rs, int rowNum) -> {
            Map<String, String> map = Maps.mutable.with();
            map.put("title", rs.getString("title"));
            return map;
        };
        List<Map<String, String>> rowList = jdbc.query("SELECT * FROM t_application", rowMapper);
        System.out.println(rowList);
    }

    /**
     * 查询单条数据
     */
    @Test
    public void testQueryForObject() {
        // 将结果集中的每行数据映射为一个对象
        RowMapper<Map<String, String>> rowMapper = (ResultSet rs, int rowNum) -> {
            Map<String, String> map = Maps.mutable.with();
            map.put("title", rs.getString("title"));
            return map;
        };
        Map<String, String> map = jdbc.queryForObject("SELECT * FROM t_application WHERE id = ?", rowMapper, 1);
        System.out.println(map);
    }

}
