package pl.wsi.consumer.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
class DirectMessageConsumer {

	@RabbitListener(queues = "direct.message.queue")
	public void receivedMessage(String message) {
		log.info("Received message: { " + message + " } \n At: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
