package rsb.rsocket.routing.service;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.routing.Customer;

import java.util.Map;

//<1>
@Controller
class RoutingController {

	private final Map<Integer, Customer> customers = Map.of(1, new Customer(1, "Zhen"), 2, new Customer(2, "Zhouyue"));

	// <2>
	@MessageMapping("customers")
	Flux<Customer> all() {
		return Flux.fromStream(this.customers.values().stream());
	}

	// <3>
	@MessageMapping("customers.{id}")
	Mono<Customer> byId(@DestinationVariable Integer id) {
		return Mono.justOrEmpty(this.customers.get(id));
	}

}
