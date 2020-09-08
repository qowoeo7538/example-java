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
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(this.passwordEncoder())
                .usersByUsernameQuery("");
    }

    /**
     * 内存方式验证用户信息
     */
    private void memoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        // 指定用户信息
        auth.inMemoryAuthentication()
                .passwordEncoder(this.passwordEncoder())
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("ADMIN_ROLE")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .authorities("USER_ROLE");
    }

    /**
     * 密码编码
     */
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
