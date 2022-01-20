package net.j7.ebook.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HelloController {

    @Autowired
    private SimpMessagingTemplate template;
    private int count = 0;

    @RequestMapping(method = RequestMethod.GET)
    public String getHello() {
        String response = "Message nº " + (++count);
        template.convertAndSend("/chat/davi", response);
        return response;
    }

}
