package net.j7.ebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//@ApiIgnore
@Controller // Dont use RestController as this method is mapping to a static file not a JSON
public class MainController {

  @RequestMapping(value={"/"})
	public String index() {
		return "index.html";
	}

}
