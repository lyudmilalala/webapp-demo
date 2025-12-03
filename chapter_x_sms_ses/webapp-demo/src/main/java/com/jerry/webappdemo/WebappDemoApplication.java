package com.jerry.webappdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebappDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappDemoApplication.class, args);
	}

	@RequestMapping(value="/healthz", method = RequestMethod.GET)
	public ResponseEntity<CommonResponse> healthz() {
		CommonResponse response = new CommonResponse();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
