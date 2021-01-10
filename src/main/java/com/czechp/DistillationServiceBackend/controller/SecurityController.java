package com.czechp.DistillationServiceBackend.controller;

import com.czechp.DistillationServiceBackend.domain.AppUser;
import com.czechp.DistillationServiceBackend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController()
@RequestMapping("/api")
@Validated()
public class SecurityController {

    private AppUserService appUserService;

    @Autowired()
    public SecurityController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(
            @RequestBody() @Valid() AppUser userToRegister,
            HttpServletRequest request
    ) {
        appUserService.registerUser(userToRegister, request);
    }

}
