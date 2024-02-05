package pl.wsi.publisher.dlq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DeadLetterQueueConfiguration {

	private static final String DLX_NAME = "dead.letter.exchange";

	@Bean
	Queue normalMessageQueue() {
		return QueueBuilder
				.durable("normal.message.queue")
				.withArgument("x-dead-letter-exchange", DLX_NAME)
				.withArgument("x-dead-letter-routing-key", "dl.normal.message.routing.key")
				.deadLetterRoutingKey("dl.normal.message.routing.key")
				.deadLetterExchange(DLX_NAME)
				.build();
	}

	@Bean
	Exchange normalExchange() {
		return ExchangeBuilder
				.directExchange("normal.message.exchange")
				.durable(true)
				.build();
	}

	@Bean
	Binding normalBinding(Queue normalMessageQueue, Exchange normalExchange) {
		return BindingBuilder
				.bind(normalMessageQueue)
				.to(normalExchange)
				.with("normal.message.routing.key")
				.noargs();
	}

	@Bean
	Queue dlMessageQueue() {
		return QueueBuilder
				.durable("deadLetter.message.queue")
				.build();
	}

	@Bean
	Exchange dlExchange() {
		return ExchangeBuilder.directExchange(DLX_NAME).durable(true).build();
	}

	@Bean
	Binding dlqBinding(Queue dlMessageQueue, Exchange dlExchange) {
		return BindingBuilder
				.bind(dlMessageQueue)
				.to(dlExchange)
				.with("dl.normal.message.routing.key")
				.noargs();
	}

	@Bean
	RabbitTemplate rabbitTemplateNormal(ConnectionFactory connectionFactory, MessageConverter messageConverter, Exchange normalExchange) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		rabbitTemplate.setExchange(normalExchange.getName());
		rabbitTemplate.setRoutingKey("normal.message.routing.key");
		return rabbitTemplate;
	}

	@Bean
	NormalMessagePublisher normalMessagePublisher(RabbitTemplate rabbitTemplateNormal) {
		return new NormalMessagePublisher(rabbitTemplateNormal);
	}

}
