package rsb.rsocket.bidirectional.client;

import io.rsocket.SocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import rsb.rsocket.BootifulProperties;

@Configuration
class ClientConfiguration {

	// <1>
	@Bean
	SocketAcceptor clientRSocketFactoryConfigurer(HealthController healthController, RSocketStrategies strategies) {
		return RSocketMessageHandler.responder(strategies, healthController);
	}

	@Bean
	RSocketRequester rSocketRequester(SocketAcceptor acceptor, RSocketRequester.Builder builder,
			BootifulProperties properties) {
		return builder//
				.rsocketConnector(rcc -> rcc.acceptor(acceptor))
				.tcp(properties.getRsocket().getHostname(), properties.getRsocket().getPort());

	}

}
