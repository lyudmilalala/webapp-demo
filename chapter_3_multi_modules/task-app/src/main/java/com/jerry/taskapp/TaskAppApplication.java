package com.jerry.taskapp;

import com.jerry.commonutils.CommonListResponse;
import com.jerry.commonutils.CommonResponse;
import com.jerry.userapp.UserDTO;
import com.jerry.userapp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskAppApplication {

	public static List<TaskDTO> taskDTOList = new LinkedList<TaskDTO>() {{
		add(new TaskDTO(1, "task1", 1, "description1", "OPEN"));
		add(new TaskDTO(2, "task2", 2, "description2", "IN_PROGRESS"));
		add(new TaskDTO(3, "task3", 3, "description3", "DONE"));
	}};

	public static void main(String[] args) {
		SpringApplication.run(TaskAppApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@RequestMapping(value="/healthz", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonResponse> healthz() {
		CommonResponse response = new CommonResponse();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/getTaskList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonListResponse<TaskVO>> getTaskList() {
		List<TaskVO> res = new LinkedList<>();
		for (TaskDTO taskDTO : taskDTOList) {
			TaskVO taskVO = new TaskVO(taskDTO);
			UserDTO userDTO = UserService.findById(taskDTO.getUserId());
			if (userDTO != null) {
				taskVO.setUsername(userDTO.getUsername());
			}
		}
		return new ResponseEntity<>(new CommonListResponse<>(res), HttpStatus.OK);
	}

	@RequestMapping(value="/createTask", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CommonResponse> createTask(TaskDTO taskDTO) {
		UserDTO userDTO = UserService.findById(taskDTO.getUserId());
		if (userDTO == null) {
			return new ResponseEntity<>(new CommonResponse(1000, "User = " + taskDTO.getUserId() + " not found"), HttpStatus.NOT_FOUND);
		}
		taskDTOList.add(taskDTO);
		return new ResponseEntity<>(new CommonResponse(), HttpStatus.OK);
	}

}