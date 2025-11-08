package com.jerry;

import com.jerry.common.response.CommonResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.jerry")
@RestController
public class WebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebDemoApplication.class, args);
	}

	@RequestMapping(value="/healthz", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonResponse> healthz() {
		CommonResponse response = new CommonResponse();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}