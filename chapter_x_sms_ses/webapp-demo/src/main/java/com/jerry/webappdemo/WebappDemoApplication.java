package com.jerry.webappdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private SendInvoiceService sendInvoiceService;

	@RequestMapping(value="/sendInvoice", method= RequestMethod.GET)
	public ResponseEntity<CommonResponse> sendInvoice(@RequestParam("orderCode") String orderCode,
													 @RequestParam("receiver") String receiver,
													  @RequestParam("attachmentBucket") String attachmentBucket,
													 @RequestParam("attachmentPath") String attachmentPath) {
		CommonResponse response = sendInvoiceService.sendInvoice(receiver, orderCode, attachmentBucket, attachmentPath);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
