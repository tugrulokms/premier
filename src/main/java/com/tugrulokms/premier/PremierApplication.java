package com.tugrulokms.premier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PremierApplication {

	public static void main(String[] args) {
		SpringApplication.run(PremierApplication.class, args);

	}
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

	}


}
