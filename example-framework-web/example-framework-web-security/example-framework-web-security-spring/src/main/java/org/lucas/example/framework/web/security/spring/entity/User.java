package org.lucas.example.framework.web.security.spring.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * spring security 用户实体
 */
public class User implements UserDetails {

    private Long id;

    private String username;

    private String password;

    /**
     * @return 用户角色权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER_ROLE"));
    }

    /**
     * @return 账号是否有效
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return 账号是否锁定，锁定账号无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return 指示用户的凭证(密码)是否已过期。过期凭据将阻止身份验证。
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return 指示是否启用或禁用用户。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
