package rsb.rsocket.requestresponse.client;

import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Log4j2
@Component
@RequiredArgsConstructor
class Client {

	private final BootifulProperties properties;

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		log.info("starting " + Client.class.getName() + '.');

		var source = RSocketConnector.create().reconnect(Retry.backoff(50, Duration.ofMillis(500)))
				.connect(TcpClientTransport.create(this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()));

		RSocketClient.from(source).requestResponse(Mono.just(DefaultPayload.create("Reactive Spring"))).doOnNext(d -> {
			log.info("Received response data {}", d.getDataUtf8());
			d.release();
		}).repeat(10).blockLast();
	}

}
