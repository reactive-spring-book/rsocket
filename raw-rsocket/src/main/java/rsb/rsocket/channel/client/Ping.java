package rsb.rsocket.channel.client;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Log4j2
@Component
@RequiredArgsConstructor
class Ping implements ApplicationListener<ApplicationReadyEvent>, Ordered {

	private final BootifulProperties properties;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

		log.info("starting " + this.getClass().getName());

		Mono<RSocket> start = RSocketFactory//
				.connect()//
				.transport(TcpClientTransport.create(
						this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()))//
				.start();

		Flux<Payload> ping = Flux.interval(Duration.ofSeconds(1))
				.map(i -> DefaultPayload.create("ping"));

		start //
				.flatMapMany(socket -> socket//
						.requestChannel(ping)//
						.map(Payload::getDataUtf8)//
						.doOnNext(str -> log
								.info("received " + str + " in " + getClass().getName()))//
						.take(10))//
				// .doFinally(signal -> socket.dispose())) //
				.then() //
				.block();
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
