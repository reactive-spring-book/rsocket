package rsb.rsocket.metadata.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
