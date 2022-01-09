package rsb.rsocket.setup.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Controller
class SetupController {

	@MessageMapping("greetings.{name}")
	Mono<String> hello(@DestinationVariable String name) {
		return Mono.just("Hello, " + name + "!");
	}

	@ConnectMapping("setup") // <1>
	public void setup(@Payload String setupPayload, @Headers Map<String, Object> headers) {// <2>
		log.info("setup payload: " + setupPayload);
		headers.forEach((k, v) -> log.info(k + '=' + v));
	}

}
