package rsb.rsocket.metadata.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.metadata.Constants;

import java.util.Locale;
import java.util.UUID;

@Slf4j
@Component
record Client(RSocketRequester rSocketRequester) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		Mono<Void> one = this.rSocketRequester// <1>
				.route("message")//
				.metadata(UUID.randomUUID().toString(), Constants.CLIENT_ID)//
				.metadata(Locale.CHINESE.getLanguage(), Constants.LANG)//
				.send();

		Mono<Void> two = this.rSocketRequester// <2>
				.route("message")//
				.metadata(metadataSpec -> {
					metadataSpec.metadata(UUID.randomUUID().toString(), Constants.CLIENT_ID);//
					metadataSpec.metadata(Locale.JAPANESE.getLanguage(), Constants.LANG);//
				})//
				.send();

		one.then(two).subscribe();
	}

}