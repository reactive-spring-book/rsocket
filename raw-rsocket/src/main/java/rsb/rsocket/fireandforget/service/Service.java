package rsb.rsocket.fireandforget.service;

import io.rsocket.*;
import io.rsocket.transport.netty.server.TcpServerTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;

@Log4j2
@Component
@RequiredArgsConstructor
class Service implements SocketAcceptor, ApplicationListener<ApplicationReadyEvent> {

	private final BootifulProperties properties;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		log.info("starting " + Service.class.getName() + '.');
		RSocketFactory //
				.receive()//
				.acceptor(this)//
				.transport(TcpServerTransport.create(
						this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()))//
				.start() //
				.subscribe();
	}

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload,
			RSocket rSocket) {
		var rs = new AbstractRSocket() {

			@Override
			public Mono<Void> fireAndForget(Payload payload) {
				log.info("new message received: " + payload.getDataUtf8());
				return Mono.empty();// <1>
			}
		};

		return Mono.just(rs);
	}

}
