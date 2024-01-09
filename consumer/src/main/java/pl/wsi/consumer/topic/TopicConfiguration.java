package pl.wsi.consumer.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class TopicConfiguration {

	public static final String INFO_LOGS = "info.logs";
	public static final String DEBUG_LOGS = "debug.logs";
	public static final String ERROR_LOGS = "error.logs";
	public static final String ALL_LOGS = "all.logs";

	@Bean
	TopicExchange topicExchange() {
		return ExchangeBuilder.topicExchange("topic.logs.exchange").durable(true).build();
	}

	@Bean
	Queue infoLogsQueue() {
		return QueueBuilder.durable(INFO_LOGS).build();
	}

	@Bean
	Queue debugLogsQueue() {
		return QueueBuilder.durable(DEBUG_LOGS).build();
	}

	@Bean
	Queue errorLogsQueue() {
		return QueueBuilder.durable(ERROR_LOGS).build();
	}

	@Bean
	Queue allLogsQueue() {
		return QueueBuilder.durable(ALL_LOGS).build();
	}

	@Bean
	Binding infoBinding(TopicExchange topicExchange, Queue infoLogsQueue) {
		return BindingBuilder.bind(infoLogsQueue).to(topicExchange).with("info.#");
	}

	@Bean
	Binding debugBinding(TopicExchange topicExchange, Queue debugLogsQueue) {
		return BindingBuilder.bind(debugLogsQueue).to(topicExchange).with("debug.#");
	}

	@Bean
	Binding errorBinding(TopicExchange topicExchange, Queue errorLogsQueue) {
		return BindingBuilder.bind(errorLogsQueue).to(topicExchange).with("error.#");
	}

	@Bean
	Binding allBinding(TopicExchange topicExchange, Queue allLogsQueue) {
		return BindingBuilder.bind(allLogsQueue).to(topicExchange).with("*.logs");
	}

	@Bean
	TopicConsumer topicConsumer() {
		return new TopicConsumer();
	}
}
