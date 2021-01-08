package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Kaixo, %s! Gu LAUGARREN TALDEA gara (Markel, Pablo, Joseba eta Aritz). Ondo Pasa!!!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "Mundua") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}