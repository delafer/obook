package net.j7.ebook.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class PingResource {

    @GetMapping(path = "/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> map = new HashMap<>();
        map.put("ping", "pong");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
