package rsb.rsocket.metadata.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetadataApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MetadataApplication.class, args);
		Thread.currentThread().join();
	}

}
