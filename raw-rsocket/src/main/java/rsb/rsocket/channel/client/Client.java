package rsb.rsocket.channel.client;

import io.rsocket.Payload;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

@Slf4j
@Component
record Client(BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		var socket = RSocketConnector//
				.create()//
				.reconnect(Retry.backoff(50, Duration.ofMillis(500)))//
				.connect(TcpClientTransport.create(this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()));
		RSocketClient//
				.from(socket)//
				.requestChannel(Flux.interval(Duration.ofSeconds(1)).map(i -> DefaultPayload.create("Hello @ " + i)))// <1>
				.map(Payload::getDataUtf8)//
				.take(10)// <2>
				.subscribe(log::info);
	}
}
