package rsb.rsocket.channel.service;

import io.rsocket.Payload;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import rsb.rsocket.BootifulProperties;

@Slf4j
@Component
record Service(BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		var socketAcceptor = SocketAcceptor.forRequestChannel(payloads -> Flux.from(payloads).map(Payload::getDataUtf8)
				.map(s -> "Echo: " + s).map(DefaultPayload::create)//
		);
		RSocketServer.create(socketAcceptor)
				.bind(TcpServerTransport.create(this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()))
				.doOnNext(cc -> log.info("server started on the address " + cc.address())) //
				.block();

	}

}
