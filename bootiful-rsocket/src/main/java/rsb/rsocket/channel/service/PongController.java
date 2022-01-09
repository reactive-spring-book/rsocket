package rsb.rsocket.channel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
class PongController {

	@MessageMapping("pong")
	Flux<String> pong(@Payload Flux<String> ping) {
		return ping.map(request -> "pong").doOnNext(log::info);
	}

}
