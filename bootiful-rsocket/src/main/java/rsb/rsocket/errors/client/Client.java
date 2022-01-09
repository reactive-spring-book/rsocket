package rsb.rsocket.errors.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Slf4j
@Component
record Client(RSocketRequester rSocketRequester) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		this.rSocketRequester//
				.route("greetings")//
				.data("Spring Fans")//
				.retrieveFlux(String.class)//
				.doOnError(e -> log.error("oops!", e))//
				.subscribe(log::info);
	}

}
