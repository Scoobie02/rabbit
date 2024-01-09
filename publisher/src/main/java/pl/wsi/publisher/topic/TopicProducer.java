package pl.wsi.publisher.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
class TopicProducer {

	private final RabbitTemplate rabbitTemplate;

	public void produceLogMessage(String logMessage, String routingKey) {
		rabbitTemplate.convertAndSend(routingKey, logMessage);
	}
}
