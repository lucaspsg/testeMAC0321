package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping()
	public String hello(){
		return "Hello Worlds";
	}


}

// AQCsKG8xdJfPmQ8l6_ffGi9fGqHza0NXz9Ruw98v8iLqQ4TUpJMM7twyo0B2yOI2WwQ9vLvUh6Q6GRflhfICDepn3e6DRGZcGW0LFkJ9zBzSpHccH638eN2ve93ggi-GQfbCO0_JgolpLIxFmY-SLPsQ-39C8nE5D6s2AswqP2hLdpMGhErf_Y4cVRExikOVddf7zW1a3XNW8Ajy4WW-dBjNMkPHQcYKnPVqHPuSHzTlpW1xV72kZ2aAL6ytuax7uA9v2OIwj00PMqlKmC9uCKHXQdu-9uZ2Qd3ZUOtjwwsHHuIVDmLa4aFI7SXE_9f19i2y5ZXFVqT-P5ce1xGpiQ