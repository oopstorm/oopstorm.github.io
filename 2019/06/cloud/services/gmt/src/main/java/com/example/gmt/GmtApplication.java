package com.example.gmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableDiscoveryClient
public class GmtApplication {

	@Value("${gmt.name}")
	private String name;

	@Autowired
	private LocalService service;

	@GetMapping
	public String gmt() {
		String gmt = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("GMT")));
		String local = service.local();
		return name + "<br>GMT: " + gmt + "<br>Local: " + local;
	}

	public static void main(String[] args) {
		SpringApplication.run(GmtApplication.class, args);
	}

}
