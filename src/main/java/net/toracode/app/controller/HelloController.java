package net.toracode.app.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

	private String sayHello() {
		return "Hello World.";
	}

}
