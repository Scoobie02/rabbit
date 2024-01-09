package pl.wsi.consumer.direct;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
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
	DirectMessageConsumer directMessageConsumer() {
		return new DirectMessageConsumer();
	}

}
