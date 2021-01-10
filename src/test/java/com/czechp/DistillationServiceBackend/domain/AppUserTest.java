package com.czechp.DistillationServiceBackend.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


@SpringBootTest()
@RunWith(SpringRunner.class)
@Slf4j()
class AppUserTest {

    @Test()
    void passwordValidatorTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AppUser user = AppUser.builder()
                .username("user")
                .password("Codhaslo55")
                .role(AppUserRole.USER.toString())
                .email("webcoderc@gmail.com")
                .enabled(true)
                .build();
        Set<ConstraintViolation<AppUser>> validate = validator.validate(user);
        validate.stream()
                .forEach(error->log.info(error.getMessage()));

        Assert.assertEquals(0, validate.size());
    }


    @Test()
    void passwordValidatorTest_passwordInvalid() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AppUser user = AppUser.builder()
                .username("user")
                .password("codhaslo55")
                .role(AppUserRole.USER.toString())
                .email("webcoderc@gmail.com")
                .enabled(true)
                .build();
        Set<ConstraintViolation<AppUser>> validate = validator.validate(user);
        validate.stream()
                .forEach(error->log.info(error.getMessage()));

        Assert.assertEquals(1, validate.size());
    }
}