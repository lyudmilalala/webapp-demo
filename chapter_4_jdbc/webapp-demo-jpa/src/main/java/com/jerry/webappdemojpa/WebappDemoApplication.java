package com.jerry.webappdemojpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	private UserRepository userRepository;
	@Autowired
	private CouponRepository couponRepository;

	@RequestMapping(value="/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<CommonListResponse<UserEntity>> getAllUsers() {
		List<UserEntity> userList = userRepository.findByUstatus("ACTIVE");
		CommonListResponse<UserEntity> response = new CommonListResponse<>(userList);
		System.out.println(response.getItems());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/getAllCoupons", method = RequestMethod.GET)
	public ResponseEntity<CommonListResponse<CouponEntity>> getAllCoupons() {
		List<CouponEntity> couponEntityList = couponRepository.findByStatus("ACTIVE");
		CommonListResponse<CouponEntity> response = new CommonListResponse<>(couponEntityList);
		System.out.println(response.getItems());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
