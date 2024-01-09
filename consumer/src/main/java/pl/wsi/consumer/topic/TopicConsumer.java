package pl.wsi.consumer.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static pl.wsi.consumer.topic.TopicConfiguration.*;

@Slf4j
class TopicConsumer {

	@RabbitListener(queues = INFO_LOGS)
	public void consumeInfoLogs(String message) {
		log.info(message);
	}

	@RabbitListener(queues = DEBUG_LOGS)
	public void consumeDebugLogs(String message) {
		log.debug(message);
	}

	@RabbitListener(queues = ERROR_LOGS)
	public void consumeErrorLogs(String message) {
		log.error(message);
	}

	@RabbitListener(queues = ALL_LOGS)
	public void consumeLog(String message) {
		log.info("From All Queue: " + message);
	}
}
