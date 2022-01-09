package rsb.rsocket.setup.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Slf4j
@Configuration
class ClientConfiguration {

	@Bean
	ApplicationRunner applicationRunner(RSocketRequester rSocketRequester) {
		return args -> rSocketRequester.route("greetings.{name}", "World").retrieveMono(String.class)
				.subscribe(log::info);
	}

	@Bean
	RSocketRequester rSocketRequester(BootifulProperties properties, RSocketRequester.Builder builder) {
		return builder.setupData("setup data!")// <1>
				.setupRoute("setup")// <2>
				.rsocketConnector(
						rSocketConnector -> rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.tcp(properties.getRsocket().getHostname(), properties.getRsocket().getPort()); // <3>
	}

}