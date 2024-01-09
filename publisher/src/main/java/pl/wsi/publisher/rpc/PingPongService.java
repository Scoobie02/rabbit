package pl.wsi.publisher.rpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
class PingPongService {

	private final RabbitTemplate rabbitTemplate;

	public String pingPong() {
		final String messageContent = "PING";
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		log.info("Client send：{}, correlationId: {}", messageContent, correlationData.getId());
		Object result = rabbitTemplate.convertSendAndReceive(RpcConfiguration.RPC_EXCHANGE, "ping.queue", messageContent, correlationData);
		log.info("Client received：{}", result);
		return (String) result;
	}

}
