package pl.wsi.publisher.rpc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
@RequiredArgsConstructor
class PingPongController {

	private final PingPongService pingPongService;

	@GetMapping("/call")
	public String pingPongCall() {
		return pingPongService.pingPong();
	}
}
