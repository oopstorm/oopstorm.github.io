package com.example.local;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "pep-logo", url = "http://192.168.1.111/pep/develop/")
public interface PepLogoService {

    @GetMapping
    String logo();

    @GetMapping("/banner")
    String banner();

}
