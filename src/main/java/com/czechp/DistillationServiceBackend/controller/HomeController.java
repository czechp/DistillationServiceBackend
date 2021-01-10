package com.czechp.DistillationServiceBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/home")
@CrossOrigin("*")
public class HomeController {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String helloEndpoint() {
        return "Welcome in Distillation service";
    }
}
