package com.czechp.DistillationServiceBackend;

import com.czechp.DistillationServiceBackend.domain.AppUser;
import com.czechp.DistillationServiceBackend.domain.AppUserRole;
import com.czechp.DistillationServiceBackend.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component()
@Profile(value = "development")
@Slf4j()
public class DevelopmentData {

    @Autowired()
    private AppUserRepository appUserRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("<-------------------Starting development service------------------->");
        appUserDevelopment();
    }

    private void appUserDevelopment() {
        AppUser user = AppUser.builder()
                .username("user")
                .password("Codhaslo55")
                .role(AppUserRole.USER.toString())
                .email("webcoderc@gmail.com")
                .enabled(true)
                .build();
        appUserRepository.save(user);

    }

}
