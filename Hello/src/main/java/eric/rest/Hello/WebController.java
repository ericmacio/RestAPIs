package eric.rest.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/api/hello")
    public Response sendHello() {
        System.out.println("api/hello");
        return new Response("Hello World");
    }
}
