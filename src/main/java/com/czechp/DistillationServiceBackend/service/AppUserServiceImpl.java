package com.czechp.DistillationServiceBackend.service;

import com.czechp.DistillationServiceBackend.domain.AppUser;
import com.czechp.DistillationServiceBackend.domain.AppUserRole;
import com.czechp.DistillationServiceBackend.exception.BadRequestException;
import com.czechp.DistillationServiceBackend.repository.AppUserRepository;
import com.czechp.DistillationServiceBackend.validator.implementation.PasswordValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@Service()
@Slf4j()
public class AppUserServiceImpl implements AppUserService {
    //time since sending email to account activate. After passed this time the account will remove
    private static final long TIME_TO_ACCOUNT_ACTIVATE = 1000 * 60 * 1;
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
            checkAccountActivation(appUser.getActivationToken());
        } else
            throw new BadRequestException("Incorrect register data");
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

    private boolean isPasswordValidated(String password) {
        PasswordValidatorImpl passwordValidator = new PasswordValidatorImpl();
        return passwordValidator.isValid(password, null);

    }

    private void checkAccountActivation(String activationToken) {
        new Thread(() -> {
            try {
                String token = activationToken;
                Thread.sleep(TIME_TO_ACCOUNT_ACTIVATE);
                AppUser appUser = appUserRepository.findByActivationToken(token)
                        .orElseThrow(() -> new RuntimeException());
                if (!appUser.isEnabled())
                    appUserRepository.delete(appUser);
                log.info("Account has removed: username: {}, email:{}", appUser.getUsername(), appUser.getEmail());
            } catch (InterruptedException e) {
                log.error("Error during checking account activation");
            }
        }).start();
    }


}
