package rsb.rsocket.metadata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import rsb.rsocket.metadata.Constants;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
class MetadataController {

	// <1>
	@ConnectMapping
	Mono<Void> setup(@Headers Map<String, Object> metadata) {
		log.info("## setup");
		return enumerate(metadata);
	}

	// <2>
	@MessageMapping("message")
	Mono<Void> message(@Header(Constants.CLIENT_ID_HEADER) String clientId, @Headers Map<String, Object> metadata) {
		log.info("## message for " + Constants.CLIENT_ID_HEADER + ' ' + clientId);
		return enumerate(metadata);
	}

	private Mono<Void> enumerate(Map<String, Object> headers) {
		headers.forEach((header, value) -> log.info(header + ':' + value));
		return Mono.empty();
	}

}
