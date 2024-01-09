package pl.wsi.consumer.fanout;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class FanoutConfiguration {

	@Bean
	Exchange fanoutExchange() {
		return ExchangeBuilder.fanoutExchange("fanout.message.exchange").durable(true).build();
	}

	@Bean
	Queue fanoutQueueOne() {
		return QueueBuilder.durable("fanout.message.queue.one").build();
	}

	@Bean
	Queue fanoutQueueTwo() {
		return QueueBuilder.durable("fanout.message.queue.two").build();
	}

	@Bean
	Binding fanoutBindingOne(Exchange fanoutExchange, Queue fanoutQueueOne) {
		return BindingBuilder.bind(fanoutQueueOne)
				.to(fanoutExchange)
				.with("fanout.message.routing.key").noargs();
	}

	@Bean
	Binding fanoutBindingTwo(Exchange fanoutExchange, Queue fanoutQueueTwo) {
		return BindingBuilder.bind(fanoutQueueTwo)
				.to(fanoutExchange)
				.with("fanout.message.routing.key").noargs();
	}

	@Bean
	FanoutMessageConsumer fanoutExchangeConsumer() {
		return new FanoutMessageConsumer();
	}
}
