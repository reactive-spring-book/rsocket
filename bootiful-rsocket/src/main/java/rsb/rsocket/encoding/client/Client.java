package rsb.rsocket.encoding.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import rsb.rsocket.GreetingRequest;
import rsb.rsocket.GreetingResponse;

@Slf4j
@Component
record Client(RSocketRequester rSocketRequester) {

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent() {
		this.rSocketRequester//
				.route("greetings")//
				.data(new GreetingRequest("Spring fans"))//
				.retrieveMono(GreetingResponse.class)//
				.subscribe(gr -> log.info(gr.toString()));
	}

}
