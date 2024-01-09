package pl.wsi.publisher.direct;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
class DirectMessagePublisher {

	private final RabbitTemplate rabbitTemplate;

	public void send(String message) {
		rabbitTemplate.convertAndSend( "Direct Exchange of Message Passed. \n Message: " + message + "\nSend at time: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}
