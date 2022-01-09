package rsb.rsocket.routing.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import rsb.rsocket.routing.Customer;

@Slf4j
@Component
record Client(RSocketRequester rSocketRequester) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		this.rSocketRequester //
				.route("customers.{id}", 1)//
				.retrieveMono(Customer.class)//
				.subscribe(c -> log.info("customers by ID:" + c));

		this.rSocketRequester//
				.route("customers")//
				.retrieveFlux(Customer.class)//
				.subscribe(c -> log.info("customers:" + c));
	}

}
