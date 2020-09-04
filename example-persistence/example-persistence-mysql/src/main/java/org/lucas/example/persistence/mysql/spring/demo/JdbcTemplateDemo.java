package org.lucas.example.persistence.mysql.spring.demo;

import org.eclipse.collections.api.factory.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.persistence.mysql.spring.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
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
    public void testTemplateQuery() {
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
    public void testTemplateQueryForObject() {
        // 将结果集中的每行数据映射为一个对象
        RowMapper<Map<String, String>> rowMapper = (ResultSet rs, int rowNum) -> {
            Map<String, String> map = Maps.mutable.with();
            map.put("title", rs.getString("title"));
            return map;
        };
        Map<String, String> map = jdbc.queryForObject("SELECT * FROM t_application WHERE id = ?", rowMapper, 1);
        System.out.println(map);
    }

    /**
     * 插入一条数据
     */
    @Test
    public void testTemplateUpdate() {
        int i = jdbc.update("INSERT INTO discover_product(product_id, product_no, product_category, create_time, update_time, is_deleted, product_status)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)", 1, 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), 1, 1);
        System.out.println(i);
    }

    @Test
    public void testTemplateUpdateAndGetKey() {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("INSERT INTO discover_live_permission(publish_name, publish_type, create_time, update_time, create_user_id, update_user_id) VALUES (?, ?, ?, ?, ?, ?)",
                Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR
        );
        // 返回Key,需要自增长主键
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList("typeName", 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "zhangsan", "zhangsan"));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        System.out.println("key: " + keyHolder.getKey().longValue());
    }

    @Test
    public void testSimpleJdbcAndGetKey() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("discover_live_permission")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> values = Maps.mutable.with();
        values.put("publish_name","publishName");
        values.put("publish_type",1);
        values.put("create_time",new Timestamp(System.currentTimeMillis()));
        values.put("update_time",new Timestamp(System.currentTimeMillis()));
        values.put("create_user_id","user_id");
        values.put("update_user_id","user_id");
        long key = jdbcInsert.executeAndReturnKey(values).longValue();
        System.out.println("key: " + key);
    }

}
