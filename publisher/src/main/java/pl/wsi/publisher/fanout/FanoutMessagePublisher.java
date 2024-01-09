package pl.wsi.publisher.fanout;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
class FanoutMessagePublisher {

	private final RabbitTemplate rabbitTemplate;

	public void send(String message) {
		rabbitTemplate.convertAndSend( "Fanout Exchange of Message Passed.\n Message: " + message + "\n Send at time: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}
