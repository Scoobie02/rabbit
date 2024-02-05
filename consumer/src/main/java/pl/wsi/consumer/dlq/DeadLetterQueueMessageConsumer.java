package pl.wsi.consumer.dlq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
class DeadLetterQueueMessageConsumer {

	@RabbitListener(queues = "normal.message.queue")
	public void receivedMessageFromNormalQueue(String message) {
		log.info("Received message from normal queue: { " + message + " } \n At: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		if(message.contains("dlq")) {
			throw new AmqpRejectAndDontRequeueException("Move to dlq");
		}
	}

	@RabbitListener(queues = "deadLetter.message.queue")
	public void receivedMessageFromDeadLetterQueue(String message) {
		log.info("Received message from dead letter queue: { " + message + " } \n At: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
