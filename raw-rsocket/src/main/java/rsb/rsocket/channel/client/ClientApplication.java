package rsb.rsocket.channel.client;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
		Thread.currentThread().join();
	}

}
