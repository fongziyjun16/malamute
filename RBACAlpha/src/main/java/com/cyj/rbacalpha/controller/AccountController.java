package com.cyj.rbacalpha.controller;

import com.cyj.rbacalpha.model.User;
import com.cyj.rbacalpha.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register/regular")
    public void register(@RequestBody User user) {
        accountService.register(user, "regular");
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasAnyAuthority('manage')")
    public void registerAdmin(@RequestBody User user) {
        accountService.register(user, "admin");
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody User user) {
        return accountService.authenticate(user);
    }

    @PostMapping("/logout")
    public void logout() {
        accountService.logout();
    }
}
