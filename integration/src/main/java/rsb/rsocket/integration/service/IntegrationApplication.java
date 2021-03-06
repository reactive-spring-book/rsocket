package rsb.rsocket.integration.service;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import rsb.rsocket.integration.GreetingRequest;
import rsb.rsocket.integration.GreetingResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class IntegrationApplication {

	@SneakyThrows
	public static void main(String args[]) {
		System.setProperty("spring.profiles.active", "service");
		SpringApplication.run(IntegrationApplication.class, args);
	}

}
