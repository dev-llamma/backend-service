package com.devfolio.banner_gen;

import com.devfolio.banner_gen.controller.ChatWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class BannerGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BannerGenApplication.class, args);
	}


}
