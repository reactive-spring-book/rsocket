package rsb.rsocket.requestresponse.service;

import io.rsocket.*;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Slf4j
@Component
record Service(BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		var transport = TcpServerTransport.create(properties.getRsocket().getHostname(),
				properties.getRsocket().getPort());
		RSocketServer
				.create(SocketAcceptor
						.forRequestResponse(p -> Mono.just(DefaultPayload.create("Hello, " + p.getDataUtf8()))))
				.bind(transport).doOnNext(cc -> log.info("server started on the address " + cc.address())).block();
	}

}
