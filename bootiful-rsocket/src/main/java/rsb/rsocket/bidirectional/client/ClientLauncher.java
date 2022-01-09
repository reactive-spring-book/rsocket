package rsb.rsocket.bidirectional.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import rsb.rsocket.GreetingResponse;

import java.time.Duration;
import java.util.stream.IntStream;

@Slf4j
@Component
record ClientLauncher(RSocketRequester rSocketRequester) {

	@EventListener
	public void ready(ApplicationReadyEvent are) {
		var maxClients = 10;
		var nestedMax = Math.max(5, (int) (Math.random() * maxClients));
		log.info("launching " + nestedMax + " clients.");
		Flux.fromStream(IntStream.range(0, nestedMax).boxed())//
				.map(id -> new Client(this.rSocketRequester, Long.toString(id)))//
				.flatMap(client -> Flux.just(client).delayElements(Duration.ofSeconds((long) (30 * Math.random()))))//
				.flatMap(Client::getGreetings)// <5>
				.subscribeOn(Schedulers.boundedElastic())// <6>
				.map(GreetingResponse::toString).subscribe(log::info);
	}

}
