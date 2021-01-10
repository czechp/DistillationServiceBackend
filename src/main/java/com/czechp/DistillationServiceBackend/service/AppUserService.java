package com.czechp.DistillationServiceBackend.service;

import com.czechp.DistillationServiceBackend.domain.AppUser;

import javax.servlet.http.HttpServletRequest;

public interface AppUserService {
    public void registerUser(AppUser appUser, HttpServletRequest request);
}
