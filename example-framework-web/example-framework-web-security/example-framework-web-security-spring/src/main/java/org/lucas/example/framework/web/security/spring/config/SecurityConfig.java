package org.lucas.example.framework.web.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
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

    /*
    @Override
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
     * ldap 方式验证用户信息
     */
    private void ldapAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapProvider = auth.ldapAuthentication();
        ldapProvider.contextSource()
                // 1 远程 ldap 服务器
                // .url("ldap://127.0.0.1:389/dc=lucas,dc=com");
                // 2.1 内嵌 ldap 服务器
                .root("dc=lucas,dc=com");
                // 2.2 指定加载 LDIF 文件路径
                // .ldif("classpath:user.ldif");

        ldapProvider
                // 指定用户基础查询，声明从people单元下查询
                .userSearchBase("ou=people")
                // 用户过滤条件
                .userSearchFilter("(uid={0})")
                // 指定组基础查询，声明从groups单元下查询
                .groupSearchBase("ou=groups")
                // 组过滤条件
                .groupSearchFilter("member={0}")
                // 将密码发送到 LDAP 服务器进行对比。（可选）
                .passwordCompare()
                // 进行加密，需要 LDAP 的密码也设置为加密
                .passwordEncoder(new BCryptPasswordEncoder())
                // 设置密码属性
                .passwordAttribute("passcode");
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
