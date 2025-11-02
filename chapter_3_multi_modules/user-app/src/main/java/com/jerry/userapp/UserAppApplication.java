package com.jerry.userapp;

import com.jerry.commonutils.CommonListResponse;
import com.jerry.commonutils.CommonResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@RestController
public class UserAppApplication {

	public static List<UserDTO> userDTOList = new LinkedList<UserDTO>() {{
		add(new UserDTO(1, "user1", "123456", "example1@email.com"));
		add(new UserDTO(2, "user2", "123456", "example2@email.com"));
		add(new UserDTO(3, "user3", "123456", "example3@email.com"));
	}};

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}

	@RequestMapping(value="/healthz", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonResponse> healthz() {
		CommonResponse response = new CommonResponse();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/getUserList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonListResponse<UserDTO>> getUserList() {
		return new ResponseEntity<>(new CommonListResponse<>(userDTOList), HttpStatus.OK);
	}
}