package rsb.rsocket.metadata.client;

import io.rsocket.Payload;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.metadata.Constants;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;

@Slf4j
record Client(EncodingUtils encodingUtils, String clientId, BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {

		var source = RSocketConnector.create().reconnect(Retry.backoff(50, Duration.ofMillis(500)))
				.connect(TcpClientTransport.create(this.properties.getRsocket().getHostname(),
						this.properties.getRsocket().getPort()));

		RSocketClient.from(source)
				.metadataPush(Mono.just(buildMetadataUpdatePayload("a-bootiful-client", Locale.JAPAN))).block();
	}

	private Payload buildMetadataUpdatePayload(String clientId, Locale locale) {
		var map = Map.<String, Object>of(Constants.LANG_HEADER, locale.getLanguage(), Constants.CLIENT_ID_HEADER,
				clientId);
		return DefaultPayload.create("", encodingUtils.encodeMetadata(map));
	}

}
