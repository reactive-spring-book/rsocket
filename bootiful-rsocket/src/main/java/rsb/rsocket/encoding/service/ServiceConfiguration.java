package rsb.rsocket.encoding.service;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;

@Configuration
class ServiceConfiguration {

	// <1>
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {// <2>
		return strategies -> strategies //
				.decoder(new Jackson2JsonDecoder())// <3>
				.encoder(new Jackson2JsonEncoder());

	}

}
