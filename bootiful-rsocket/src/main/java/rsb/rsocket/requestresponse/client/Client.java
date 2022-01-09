package rsb.rsocket.requestresponse.client;

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
		log.info("the data mimeType is " + this.rSocketRequester.dataMimeType());// <3>
		log.info("the metadata mimeType is " + this.rSocketRequester.metadataMimeType());
		this.rSocketRequester//
				.route("greeting")// <4>
				.data("Reactive Spring")// <5>
				.retrieveMono(String.class)// <6>
				.subscribe(System.out::println);
	}

}
