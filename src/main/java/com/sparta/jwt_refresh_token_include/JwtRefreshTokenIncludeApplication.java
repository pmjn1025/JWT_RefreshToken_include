package com.sparta.jwt_refresh_token_include;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class JwtRefreshTokenIncludeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtRefreshTokenIncludeApplication.class, args);
	}

}
