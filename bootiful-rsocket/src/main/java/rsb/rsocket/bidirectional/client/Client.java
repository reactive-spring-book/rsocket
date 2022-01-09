package rsb.rsocket.bidirectional.client;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import rsb.rsocket.GreetingRequest;
import rsb.rsocket.GreetingResponse;

record Client(RSocketRequester rSocketRequester, String uid) {

	Flux<GreetingResponse> getGreetings() { //
		return rSocketRequester()//
				.route("greetings")//
				.data(new GreetingRequest("Client #" + this.uid))//
				.retrieveFlux(GreetingResponse.class);
	}

}
