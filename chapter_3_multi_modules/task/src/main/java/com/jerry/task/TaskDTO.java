package com.jerry.task;

import lombok.Data;

@Data
public class TaskDTO {
    private int id;
    private String taskName;
    private Integer userId;
    private String description;
    private String status;

    public TaskDTO(int id, String taskName, Integer userId, String description, String status) {
        this.id = id;
        this.taskName = taskName;
        this.userId = userId;
        this.description = description;
        this.status = status;
    }
}