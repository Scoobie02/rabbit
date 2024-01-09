package pl.wsi.publisher.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic/logs")
@RequiredArgsConstructor
class TopicController {
	
	private final TopicProducer topicProducer;
	
	@PostMapping("/info")
	public void publishInfoLog(@RequestBody String logMessage) {
		topicProducer.produceLogMessage(logMessage, "info.logs");
	}

	@PostMapping("/debug")
	public void publishDebugLog(@RequestBody String logMessage) {
		topicProducer.produceLogMessage(logMessage, "debug.logs");
	}

	@PostMapping("/error")
	public void publishErrorLog(@RequestBody String logMessage) {
		topicProducer.produceLogMessage(logMessage, "error.logs");
	}
}
