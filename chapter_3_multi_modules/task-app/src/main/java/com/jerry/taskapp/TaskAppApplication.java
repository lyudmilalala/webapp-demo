package com.jerry.taskapp;

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
public class TaskAppApplication {

	public static List<TaskDTO> taskDTOList = new LinkedList<TaskDTO>() {{
		add(new TaskDTO(1, "task1", "description1", "OPEN"));
		add(new TaskDTO(2, "task2", "description2", "IN_PROGRESS"));
		add(new TaskDTO(3, "task3", "description3", "DONE"));
	}};

	public static void main(String[] args) {
		SpringApplication.run(TaskAppApplication.class, args);
	}

	@RequestMapping(value="/healthz", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonResponse> healthz() {
		CommonResponse response = new CommonResponse();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/getTaskList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CommonListResponse<TaskDTO>> getTaskList() {
		return new ResponseEntity<>(new CommonListResponse<>(taskDTOList), HttpStatus.OK);
	}
}