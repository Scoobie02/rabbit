package pl.wsi.publisher.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class TopicConfiguration {

	public static final String TOPIC_LOGS_EXCHANGE = "topic.logs.exchange";

	@Bean
	Declarables topicBindings() {
		TopicExchange topicExchange = ExchangeBuilder.topicExchange(TOPIC_LOGS_EXCHANGE).durable(true).build();

		Queue infoLogsQueue = QueueBuilder.durable("info.logs").build();
		Queue debugLogsQueue = QueueBuilder.durable("debug.logs").build();
		Queue errorLogsQueue = QueueBuilder.durable("error.logs").build();
		Queue allLogsQueue = QueueBuilder.durable("all.logs").build();

		return new Declarables(
				topicExchange,
				infoLogsQueue, debugLogsQueue, errorLogsQueue, allLogsQueue,
				BindingBuilder.bind(infoLogsQueue).to(topicExchange).with("info.#"),
				BindingBuilder.bind(debugLogsQueue).to(topicExchange).with("debug.#"),
				BindingBuilder.bind(errorLogsQueue).to(topicExchange).with("error.#"),
				BindingBuilder.bind(allLogsQueue).to(topicExchange).with("*.logs")
		);
	}

	@Bean
	RabbitTemplate topicRabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setExchange(TOPIC_LOGS_EXCHANGE);
		rabbitTemplate.setConnectionFactory(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}

	@Bean
	TopicProducer topicProducer(RabbitTemplate topicRabbitTemplate) {
		return new TopicProducer(topicRabbitTemplate);
	}
}
