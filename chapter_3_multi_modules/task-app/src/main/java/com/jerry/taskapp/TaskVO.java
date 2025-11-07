package com.jerry.taskapp;

import lombok.Data;

@Data
public class TaskVO {
    private int id;
    private String taskName;
    private Integer userId;
    private String username;
    private String description;
    private String status;

    public TaskVO(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.taskName = taskDTO.getTaskName();
        this.userId = taskDTO.getUserId();
        this.description = taskDTO.getDescription();
        this.status = taskDTO.getStatus();
    }
}