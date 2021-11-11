package com.thecodinginterface.tcilol.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;

    @JsonIgnore
    private String password;

    private String accessToken;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsDto(){}

    public UserDetailsDto(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    public UserDetailsDto(String accessToken, User user) {
        this(user);
        this.accessToken = accessToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getId() {
        return id;
    }
}
