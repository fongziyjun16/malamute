package com.cyj.rbacalpha.service;

import com.cyj.rbacalpha.exception.AuthenticationFailureException;
import com.cyj.rbacalpha.exception.UserAlreadyExistException;
import com.cyj.rbacalpha.model.LoginUser;
import com.cyj.rbacalpha.model.Role;
import com.cyj.rbacalpha.model.User;
import com.cyj.rbacalpha.repository.UserRepository;
import com.cyj.rbacalpha.util.JwtUtil;
import com.cyj.rbacalpha.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(User user, String role) {
        User dbUser = userRepository.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            throw new UserAlreadyExistException("User Already Exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.builder().name(role).build());
        userRepository.save(user);
    }

    public String authenticate(User user) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationFailureException("Invalid Username or Password");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        redisUtil.setWithExpiration("login:" + user.getUsername(), loginUser, 60 * 60 * 12);
        return jwtUtil.generateToken(user.getUsername());
    }

    public void logout() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String username = loginUser.getUsername();
        redisUtil.delete("login:" + username);
    }

}
