package rsb.rsocket.fireandforget.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
class GreetingController {

	@MessageMapping("greeting")
	void greetName(String name) {
		log.info("new command sent to update the name '" + name + "'.");
	}

}
