package rsb.rsocket.metadata.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.metadata.Constants;

@Slf4j
@Component
record Service(EncodingUtils encodingUtils, BootifulProperties properties) {

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		var rsocket = properties.getRsocket();
		var transport = TcpServerTransport.create(rsocket.getHostname(), rsocket.getPort());
		var socket = new RSocket() {
			@Override
			public Mono<Void> metadataPush(Payload payload) {
				var metadataUtf8 = payload.getMetadataUtf8();
				var metadata = encodingUtils.decodeMetadata(metadataUtf8);
				var clientId = (String) metadata.get(Constants.CLIENT_ID_HEADER);
				var stringBuilder = new StringBuilder() //
						.append(System.lineSeparator())
						.append(String.format("(%s) %s", clientId, "---------------------------------"))//
						.append(System.lineSeparator());
				metadata.forEach((k, v) -> stringBuilder//
						.append(String.format("(%s) %s", clientId, k + '=' + v))//
						.append(System.lineSeparator()));
				log.info(stringBuilder.toString());
				return Mono.empty();
			}
		};
		var socketAcceptor = SocketAcceptor.with(socket);
		RSocketServer //
				.create(socketAcceptor) //
				.bind(transport) //
				.doOnNext(cc -> log.info("server started on the address " + cc.address())) //
				.block();
	}
}
