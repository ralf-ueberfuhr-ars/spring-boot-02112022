package de.sample.schulung.todossample;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    //@RequestMapping(value="hello", method = RequestMethod.GET)
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sayHello(
      @RequestParam(value = "name", defaultValue = "World")
      String name
    ) {
        return String.format("<h1>Hello</h1> %s!", name);
    }

    @GetMapping(value = "/2", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sayHello2() {
        return "Hello World 2!";
    }

}
