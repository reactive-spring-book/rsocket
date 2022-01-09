package rsb.rsocket.channel.service;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Slf4j
@Component
record Pong(BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() throws Exception {

		/*
		 * var hostname = this.properties.getRsocket().getHostname(); var port =
		 * this.properties.getRsocket().getPort(); var channel = RSocketServer //
		 * .create(this) // .payloadDecoder(PayloadDecoder.ZERO_COPY) //
		 * .bind(TcpServerTransport.create(hostname, port)) .block(); channel.onClose();
		 *
		 *
		 * Mono<RSocket> source = RSocketConnector.create() .reconnect(Retry.backoff(50,
		 * Duration.ofMillis(500))) .connect(TcpClientTransport.create("localhost",
		 * 7000));
		 *
		 * RSocketClient.from(source)
		 * .requestResponse(Mono.just(DefaultPayload.create("Test Request")))
		 * .doOnSubscribe(s -> log.info("Executing Request")) .doOnNext(d -> {
		 * log.info("Received response data {}", d.getDataUtf8()); d.release(); })
		 * .repeat(10) .blockLast();
		 */

	}

	/*
	 * var rs = new AbstractRSocket() {
	 *
	 * @Override public Flux<Payload> requestChannel(Publisher<Payload> payloads) { // <1>
	 * return Flux // .from(payloads) // .map(Payload::getDataUtf8) // .doOnNext(str ->
	 * log.info("received " + str + " in " + getClass())) // .map(request -> "pong") //
	 * <2> .map(DefaultPayload::create); } };
	 *
	 * return Mono.just(rs);
	 */
	// }

}
