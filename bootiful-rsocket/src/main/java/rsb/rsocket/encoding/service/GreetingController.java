package rsb.rsocket.encoding.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import rsb.rsocket.GreetingRequest;
import rsb.rsocket.GreetingResponse;

import java.util.Map;

@Slf4j
@Controller
class GreetingController {

	@MessageMapping("greetings")
	Mono<GreetingResponse> greet(@Payload GreetingRequest request, @Headers Map<String, Object> headers) {
		headers.forEach((k, v) -> log.info(k + '=' + v));
		return Mono.just(new GreetingResponse("Hello, " + request.name() + "!"));
	}

}
