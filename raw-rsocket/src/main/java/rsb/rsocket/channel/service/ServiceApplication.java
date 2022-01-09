package rsb.rsocket.channel.service;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Handles channel requests. Run this first.
 */
@SpringBootApplication
public class ServiceApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
		Thread.currentThread().join();
	}

}
