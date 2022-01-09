package rsb.rsocket.bidirectional.service;

import io.rsocket.*;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.bidirectional.ClientHealthState;
import rsb.rsocket.bidirectional.GreetingRequest;
import rsb.rsocket.bidirectional.GreetingResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static rsb.rsocket.bidirectional.ClientHealthState.STOPPED;

@Component
record Service(BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {

		var socketAcceptor = SocketAcceptor.forRequestChannel(payloads -> Flux.from(payloads).map(Payload::getDataUtf8)
				.map(s -> "Echo: " + s).map(DefaultPayload::create));
		RSocketServer.create(socketAcceptor).bindNow(
				TcpServerTransport.create(properties.getRsocket().getHostname(), properties.getRsocket().getPort()));

	}

}
