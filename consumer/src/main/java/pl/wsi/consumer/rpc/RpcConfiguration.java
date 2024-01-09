package pl.wsi.consumer.rpc;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class RpcConfiguration {

	public static final String RPC_EXCHANGE = "rpc_exchange";

	@Bean
	RabbitTemplate rpcRabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter, TopicExchange rpcTopicExchange) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		rabbitTemplate.setExchange(rpcTopicExchange.getName());
		return rabbitTemplate;
	}

	@Bean
	PingPongService pingPongService(RabbitTemplate rpcRabbitTemplate) {
		return new PingPongService(rpcRabbitTemplate);
	}

	@Bean
	Queue pingQueue() {
		return QueueBuilder.durable("ping.queue").build();
	}

	@Bean
	Queue pongQueue() {
		return QueueBuilder.durable("pong.queue").build();
	}

	@Bean
	TopicExchange rpcTopicExchange() {
		return new TopicExchange(RPC_EXCHANGE);
	}

	@Bean
	Binding pingBinding(Queue pingQueue, TopicExchange rpcTopicExchange) {
		return BindingBuilder.bind(pingQueue).to(rpcTopicExchange).with("ping.queue");
	}

	@Bean
	Binding pongBinding(Queue pongQueue, TopicExchange rpcTopicExchange) {
		return BindingBuilder.bind(pongQueue).to(rpcTopicExchange).with("pong.queue");
	}
}
