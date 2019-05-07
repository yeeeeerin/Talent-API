package talent.com.wabapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class controller {
    @GetMapping("/hi")
    public String hi(){
        return "h1 webapp";
    }
}
