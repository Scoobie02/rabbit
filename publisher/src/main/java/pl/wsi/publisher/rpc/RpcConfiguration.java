package pl.wsi.publisher.rpc;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class RpcConfiguration {

	public static final String RPC_EXCHANGE = "rpc_exchange";
	public static final String PING_QUEUE = "ping.queue";
	public static final String PONG_QUEUE = "pong.queue";

	@Bean
	Queue pingQueue() {
		return QueueBuilder.durable(PING_QUEUE).build();
	}

	@Bean
	Queue pongQueue() {
		return QueueBuilder.durable(PONG_QUEUE).build();
	}

	@Bean
	TopicExchange rpcExchange() {
		return new TopicExchange(RPC_EXCHANGE);
	}

	@Bean
	Binding pingBinding(Queue pingQueue, TopicExchange rpcExchange) {
		return BindingBuilder.bind(pingQueue).to(rpcExchange).with("ping.queue");
	}

	@Bean
	Binding pongBinding(Queue pongQueue, TopicExchange rpcExchange) {
		return BindingBuilder.bind(pongQueue).to(rpcExchange).with("pong.queue");
	}

	@Bean
	RabbitTemplate rpcRabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		template.setExchange(RPC_EXCHANGE);
		template.setReplyAddress(PONG_QUEUE);
		template.setReplyTimeout(6000);
		return template;
	}

	@Bean
	SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory, RabbitTemplate rpcRabbitTemplate) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(PONG_QUEUE);
		container.setMessageListener(rpcRabbitTemplate);
		return container;
	}

	@Bean
	PingPongService pingPongService(RabbitTemplate rpcRabbitTemplate) {
		return new PingPongService(rpcRabbitTemplate);
	}
}
