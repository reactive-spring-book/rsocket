package rsb.rsocket.bidirectional.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.bidirectional.GreetingResponse;

import java.time.Duration;
import java.util.stream.IntStream;

@Slf4j
@Component
record ClientLauncher(EncodingUtils encodingUtils, BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() throws Exception {
		var maxClients = 10;
		var nestedMax = Math.max(5, (int) (Math.random() * maxClients));
		var hostname = this.properties.getRsocket().getHostname();// <1>
		var port = this.properties.getRsocket().getPort();
		log.info("launching " + nestedMax + " clients connecting to " + hostname + ':' + port + ".");
		Flux.fromStream(IntStream.range(0, nestedMax).boxed())// <2>
				.map(id -> new Client(this.encodingUtils, Long.toString(id), hostname, port))// <3>
				.flatMap(client -> Flux.just(client).delayElements(Duration.ofSeconds((long) (30 * Math.random()))))// <4>
				.flatMap(Client::getGreetings)// <5>
				.map(GreetingResponse::toString)// <6>
				.subscribe(log::info);
	}

}