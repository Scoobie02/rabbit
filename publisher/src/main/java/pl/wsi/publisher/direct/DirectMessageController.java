package pl.wsi.publisher.direct;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direct")
@RequiredArgsConstructor
class DirectMessageController {

	private final DirectMessagePublisher directMessagePublisher;

	@PostMapping("/publish")
	public void publishDirectMessage(@RequestBody String message) {
		directMessagePublisher.send(message);
	}
}
