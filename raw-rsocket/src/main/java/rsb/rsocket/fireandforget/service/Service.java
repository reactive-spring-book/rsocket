package rsb.rsocket.fireandforget.service;

import io.rsocket.*;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.metadata.Constants;

@Slf4j
@Component
record Service(EncodingUtils encodingUtils, BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		var transport = TcpServerTransport.create(properties.getRsocket().getHostname(),
				properties.getRsocket().getPort());
		var socket = new RSocket() {
			@Override
			public Mono<Void> fireAndForget(Payload payload) {
				var request = payload.getDataUtf8();
				log.info("received " + request + '.');
				return Mono.empty();
			}
		};
		var socketAcceptor = SocketAcceptor.with(socket);
		RSocketServer //
				.create(socketAcceptor) //
				.bind(transport) //
				.doOnNext(cc -> log.info("server started on the address " + cc.address())) //
				.block();
	}
}

/*
 *
 * @Log4j2
 *
 * @Component
 *
 * @RequiredArgsConstructor class Service {
 *
 * // implements SocketAcceptor, ApplicationListener<ApplicationReadyEvent> {
 *
 */
/*
 * private final BootifulProperties properties;
 *
 * @Override public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
 * log.info("starting " + Service.class.getName() + '.'); RSocketFactory // .receive()//
 * .acceptor(this)//
 * .transport(TcpServerTransport.create(this.properties.getRsocket().getHostname(),
 * this.properties.getRsocket().getPort()))// .start() // .subscribe(); }
 *
 * @Override public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload,
 * RSocket rSocket) { var rs = new AbstractRSocket() {
 *
 * @Override public Mono<Void> fireAndForget(Payload payload) {
 * log.info("new message received: " + payload.getDataUtf8()); return Mono.empty();// <1>
 * } };
 *
 * return Mono.just(rs); }
 *//*
	 *
	 *
	 * }
	 */
