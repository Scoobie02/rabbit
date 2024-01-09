package pl.wsi.publisher.direct;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class DirectConfiguration {

	@Bean
	Exchange directExchange() {
		return ExchangeBuilder.directExchange("direct.message.exchange").durable(true).build();
	}
	@Bean
	Queue directMessageQueue() {
		return QueueBuilder.nonDurable("direct.message.queue").build();
	}

	@Bean
	Binding binding(Queue directMessageQueue, Exchange directExchange) {
		return BindingBuilder
				.bind(directMessageQueue)
				.to(directExchange)
				.with("direct.message.routing.key")
				.noargs();
	}

	@Bean
	RabbitTemplate rabbitTemplateDirect(ConnectionFactory connectionFactory, MessageConverter messageConverter, Exchange directExchange) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		rabbitTemplate.setExchange(directExchange.getName());
		rabbitTemplate.setRoutingKey("direct.message.routing.key");
		return rabbitTemplate;
	}

	@Bean
	DirectMessagePublisher directExchangeSender(RabbitTemplate rabbitTemplateDirect) {
		return new DirectMessagePublisher(rabbitTemplateDirect);
	}
}