package com.jerry.taskapp;

import lombok.Data;

@Data
public class TaskDTO {
    private int id;
    private String taskName;
    private String description;
    private String status;

    public TaskDTO(int id, String taskName, String description, String status) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }
}