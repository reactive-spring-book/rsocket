package rsb.rsocket.bidirectional.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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

@Slf4j
@Component
@RequiredArgsConstructor
class Service implements SocketAcceptor {

	private final BootifulProperties properties;

	private final EncodingUtils encodingUtils;

	@EventListener(ApplicationReadyEvent.class)
	public void ready() throws Exception {

		RSocketServer//
				.create((setup, sendingSocket) -> Mono.just(new RSocket() {
					@Override
					public Flux<Payload> requestStream(Payload payload) {
						return doStream(sendingSocket, payload);
					}
				}))//
				.bind(TcpServerTransport.create(this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort())) //
				.doOnNext(cc -> log.info("server started on the address " + cc.address())) //
				.block();
	}

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket clientRsocket) {

		// <1>
		return Mono.just(new RSocket() {

			@Override
			public Flux<Payload> requestStream(Payload payload) {

				// <2>
				var clientHealthStateFlux = clientRsocket//
						.requestStream(DefaultPayload.create(new byte[0]))//
						.map(p -> encodingUtils.decode(p.getDataUtf8(), ClientHealthState.class))//
						.filter(chs -> chs.state().equalsIgnoreCase(STOPPED));

				// <3>
				var replyPayloadFlux = Flux//
						.fromStream(Stream.generate(() -> {
							var greetingRequest = encodingUtils.decode(payload.getDataUtf8(), GreetingRequest.class);
							var message = "Hello, " + greetingRequest.name() + " @ " + Instant.now() + "!";
							return new GreetingResponse(message);
						}))//
						.delayElements(Duration.ofSeconds(Math.max(3, (long) (Math.random() * 10))))//
						.doFinally(signalType -> log.info("finished."));

				return replyPayloadFlux // <4>
						.takeUntilOther(clientHealthStateFlux)//
						.map(encodingUtils::encode)//
						.map(DefaultPayload::create);
			}
		});
	}

	private Flux<Payload> doStream(RSocket clientRsocket, Payload payload) {

		// <2>
		var clientHealthStateFlux = clientRsocket//
				.requestStream(DefaultPayload.create(new byte[0]))//
				.map(p -> encodingUtils.decode(p.getDataUtf8(), ClientHealthState.class))//
				.filter(chs -> chs.state().equalsIgnoreCase(STOPPED));

		// <3>
		var replyPayloadFlux = Flux//
				.fromStream(Stream.generate(() -> {
					var greetingRequest = encodingUtils.decode(payload.getDataUtf8(), GreetingRequest.class);
					var message = "Hello, " + greetingRequest.name() + " @ " + Instant.now() + "!";
					return new GreetingResponse(message);
				}))//
				.delayElements(Duration.ofSeconds(Math.max(3, (long) (Math.random() * 10))))//
				.doFinally(signalType -> log.info("finished."));

		return replyPayloadFlux // <4>
				.takeUntilOther(clientHealthStateFlux)//
				.map(encodingUtils::encode)//
				.map(DefaultPayload::create);
	}

}