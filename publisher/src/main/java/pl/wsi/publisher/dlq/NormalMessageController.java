package pl.wsi.publisher.dlq;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/normal")
@RequiredArgsConstructor
class NormalMessageController {

	private final NormalMessagePublisher normalMessagePublisher;

	@PostMapping("/publish")
	public void publishNormalMessage(@RequestBody String message) {
		normalMessagePublisher.send(message);
	}

}
