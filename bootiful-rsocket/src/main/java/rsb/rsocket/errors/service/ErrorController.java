package rsb.rsocket.errors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@Controller
class ErrorController {

	@MessageMapping("greetings")
	Flux<String> greet(String name) {// <1>
		return Flux//
				.fromStream(Stream.generate(() -> "hello " + name + "!"))//
				.flatMap(message -> {
					if (Math.random() >= .5) {
						return Mono.error(new IllegalArgumentException("Ooops!"));
					} //
					else {
						return Mono.just(message);
					}
				})//
				.delayElements(Duration.ofSeconds(1));
	}

	@MessageExceptionHandler // <2>
	void exception(Exception exception) {
		log.error("the exception is " + exception.getMessage());
	}

}
