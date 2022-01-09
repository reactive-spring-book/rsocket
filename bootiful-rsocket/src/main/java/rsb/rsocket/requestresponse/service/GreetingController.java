package rsb.rsocket.requestresponse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Map;

//<1>
@Slf4j
@Controller
class GreetingController {

	// <2> <3>
	@MessageMapping("greeting")
	Mono<String> greet(@Headers Map<String, Object> headers, // <4>
			@Payload String name// <5>
	) {
		headers.forEach((k, v) -> log.info(k + '=' + v));
		return Mono.just("Hello, " + name + "!");
	}

}
