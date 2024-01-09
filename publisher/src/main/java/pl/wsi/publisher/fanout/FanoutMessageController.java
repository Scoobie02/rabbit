package pl.wsi.publisher.fanout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fanout")
@RequiredArgsConstructor
class FanoutMessageController {

	private final FanoutMessagePublisher fanoutMessagePublisher;

	@PostMapping("/publish")
	public void publishFanoutMessage(@RequestBody String message) {
		fanoutMessagePublisher.send(message);
	}
}
