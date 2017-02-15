package net.toracode.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan
@EnableScheduling
public class CronEmailProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronEmailProjectApplication.class, args);
	}
}
