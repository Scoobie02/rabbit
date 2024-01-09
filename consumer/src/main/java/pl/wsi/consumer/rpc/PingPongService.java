package pl.wsi.consumer.rpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RequiredArgsConstructor
class PingPongService {

	private final RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = "ping.queue")
	public void process(Message msg) {
		log.info("server receive : {}", msg.toString());
		rabbitTemplate.convertAndSend(RpcConfiguration.RPC_EXCHANGE, "pong.queue", "PONG", m -> {
			m.getMessageProperties().setCorrelationId(msg.getMessageProperties().getCorrelationId());
			return m;});
	}
}
