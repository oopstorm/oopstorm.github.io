package com.example.local;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "home", url = "http://192.168.1.122/home")
public interface HomeService {

    @GetMapping("/index")
    String index();

}
