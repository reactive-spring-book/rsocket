package rsb.rsocket.channel.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
class Client implements ApplicationListener<ApplicationReadyEvent> {

	private final RSocketRequester rSocketRequester;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		var ping = Flux//
				.interval(Duration.ofSeconds(1))//
				.map(i -> "ping");
		rSocketRequester//
				.route("pong")//
				.data(ping)//
				.retrieveFlux(String.class)//
				.subscribe(log::info);
	}

}
