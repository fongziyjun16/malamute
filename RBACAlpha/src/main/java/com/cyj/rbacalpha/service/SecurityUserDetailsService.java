package com.cyj.rbacalpha.service;

import com.cyj.rbacalpha.exception.AuthenticationFailureException;
import com.cyj.rbacalpha.model.Authority;
import com.cyj.rbacalpha.model.LoginUser;
import com.cyj.rbacalpha.model.Role;
import com.cyj.rbacalpha.model.User;
import com.cyj.rbacalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userRepository.findUserByUsername(username);
        if (dbUser == null) {
            throw new AuthenticationFailureException("Authentication Failure");
        }
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = dbUser.getRoles();
        for (Role role : roles) {
            Set<Authority> authorities = role.getAuthorities();
            for (Authority authority : authorities) {
                permissions.add(authority.getName());
            }
        }
        return new LoginUser(dbUser, permissions);
    }
}
