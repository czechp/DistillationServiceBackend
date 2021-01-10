package com.czechp.DistillationServiceBackend.service;

import com.czechp.DistillationServiceBackend.domain.AppUser;
import com.czechp.DistillationServiceBackend.domain.AppUserRole;
import com.czechp.DistillationServiceBackend.repository.AppUserRepository;
import com.czechp.DistillationServiceBackend.validator.implementation.PasswordValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.UUID;

@Service()
public class AppUserServiceImpl implements AppUserService {
    private AppUserRepository appUserRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired()
    public AppUserServiceImpl(AppUserRepository appUserRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(AppUser appUser, HttpServletRequest request) {
        boolean saveNewUserConditions = !existsByEmail(appUser.getEmail())
                && !existsByUsername(appUser.getUsername())
                && isPasswordValidated(appUser.getPassword());
        
        if (saveNewUserConditions) {
            appUser.setActivationToken(UUID.randomUUID().toString());
            appUser.setRole(AppUserRole.USER.toString());
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            HashMap<String, String> email = prepareActivationEmail(appUser, request);
            emailService.sendEmail(email.get("address"), email.get("subject"), email.get("body"));
            appUserRepository.save(appUser);
        }
    }

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    private HashMap<String, String> prepareActivationEmail(AppUser appUser, HttpServletRequest request) {
        String body = new StringBuilder()
                .append("To activate your account in Distillation service click this link: \n")
                .append("http://")
                .append(request.getLocalAddr())
                .append(":")
                .append(request.getLocalPort())
                .append("/api/activation?token=")
                .append(appUser.getActivationToken())
                .toString();

        HashMap<String, String> emailHashMap = new HashMap<>();
        emailHashMap.put("address", appUser.getEmail());
        emailHashMap.put("subject", "Distillation service account confirmation");
        emailHashMap.put("body", body);

        return emailHashMap;
    }

    private boolean isPasswordValidated(String password){
        PasswordValidatorImpl passwordValidator = new PasswordValidatorImpl();
        return passwordValidator.isValid(password, null);

    }



}
