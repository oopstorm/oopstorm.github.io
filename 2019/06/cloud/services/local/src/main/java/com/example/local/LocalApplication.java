package com.example.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class LocalApplication {

	@Autowired
	HomeService homeService;

	@Autowired
	PepLogoService logoService;

	@GetMapping
	public String local() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	@GetMapping("/logo")
	public String logo() {
		return logoService.logo();
	}

	@GetMapping("/banner")
	public String banner() {
		return logoService.banner();
	}

	@GetMapping("/index")
	public String index() {
		return homeService.index();
	}

	public static void main(String[] args) {
		SpringApplication.run(LocalApplication.class, args);
	}

}
