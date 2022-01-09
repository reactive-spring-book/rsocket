package rsb.rsocket.metadata.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;

import java.util.UUID;

@SpringBootApplication
public class MetadataApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MetadataApplication.class, args);
		Thread.currentThread().join();
	}

	/*
	 * @Bean Client one(BootifulProperties properties, EncodingUtils encodingUtils) {
	 * return new Client(encodingUtils, UUID.randomUUID().toString(), properties); }
	 *
	 * @Bean Client two(BootifulProperties properties, EncodingUtils encodingUtils) {
	 * return new Client(encodingUtils, UUID.randomUUID().toString(), properties); }
	 */

}
