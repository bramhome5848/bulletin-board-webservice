package com.lkj.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing	//JPA Auditing활성화
public class BulletinBoardWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulletinBoardWebserviceApplication.class, args);
	}

}
