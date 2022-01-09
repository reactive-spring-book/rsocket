package rsb.rsocket.integration.service;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegrationApplication {

	@SneakyThrows
	public static void main(String args[]) {
		System.setProperty("spring.profiles.active", "service");
		SpringApplication.run(IntegrationApplication.class, args);
	}

}
