package pl.wsi.consumer.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
class FanoutMessageConsumer {

	@RabbitListener(queues = "fanout.message.queue.one")
	public void consumeMessageQueue1(String message) {
		log.info("From queue one.");
		receivedMessage(message);
	}

	@RabbitListener(queues = "fanout.message.queue.two")
	public void consumeMessageQueue2(String message) {
		log.info("From queue two.");
		receivedMessage(message);
	}

	private void receivedMessage(String message) {
		log.info("Received message: { " + message + " } \n At: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
