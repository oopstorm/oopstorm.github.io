package com.example.gmt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("local")
@RequestMapping("/local")
public interface LocalService {

    @GetMapping
    String local();

}
