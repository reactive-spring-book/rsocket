package rsb.rsocket.fireandforget.client;

//import io.rsocket.RSocketFactory;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rsb.rsocket.BootifulProperties;

@Log4j2
@Component

record Client(BootifulProperties properties) {

	/*
	 *
	 * @Override public void onApplicationEvent(ApplicationReadyEvent
	 * applicationReadyEvent) { log.info("starting " + Client.class.getName() + '.');
	 * RSocketServer// // .connect()//
	 * .transport(TcpClientTransport.create(this.properties.getRsocket().getHostname(),
	 * this.properties.getRsocket().getPort()))// .start()// .flatMapMany(socket ->
	 * socket.fireAndForget(DefaultPayload.create("Reactive Spring")))// <1>
	 * .subscribe(log::info); }
	 */

}
