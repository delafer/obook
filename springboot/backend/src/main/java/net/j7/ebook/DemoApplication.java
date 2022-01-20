package net.j7.ebook;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EntityScan("net.j7.ebook")
public class DemoApplication implements ApplicationRunner, WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        System.out.println("Application started ... launching browser now");
        openHomePage("http://localhost:4300/");
    }

    private void openHomePage(String s) {
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}
