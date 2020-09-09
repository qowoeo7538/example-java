package org.lucas.example.framework.web.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 无权限访问
                .antMatchers("/service/test").permitAll()
                // ADMIN_ROLE 角色才能访问该路径
                .antMatchers("/service/admin").hasRole("ADMIN_ROLE")
                // USER_ROLE 角色才能访问该路径
                .antMatchers("/service/user").hasRole("USER_ROLE");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存方式验证用户信息
        // memoryAuthentication(auth);
        // JDBC 方式验证用户信息
        // jdbcAuthentication(auth);
    }

    /**
     * JDBC 方式验证用户信息
     */
    private void jdbcAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username,authority from authorities where username = ?")
                .groupAuthoritiesByUsername("select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id");
    }

    /**
     * 内存方式验证用户信息
     */
    private void memoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        // 指定用户信息
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ADMIN_ROLE")
                .and()
                .withUser("user")
                .password(passwordEncoder.encode("user"))
                .authorities("USER_ROLE");
    }

}
