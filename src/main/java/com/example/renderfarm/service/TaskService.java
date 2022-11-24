package com.example.renderfarm.service;

import com.example.renderfarm.dto.request.CreateTaskRequestDto;
import com.example.renderfarm.dto.response.GetAllTasksResponseDto;
import com.example.renderfarm.dto.response.GetTaskHistoryResponseDto;

import java.util.List;

public interface TaskService {
    void createTask(CreateTaskRequestDto createTaskRequestDto);

    List<GetAllTasksResponseDto> getAllTasks();

    List<GetTaskHistoryResponseDto> getTaskUpdatesHistory(Long id);
}
