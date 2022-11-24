package com.example.renderfarm.controller;

import com.example.renderfarm.dto.request.CreateTaskRequestDto;
import com.example.renderfarm.dto.response.GetAllTasksResponseDto;
import com.example.renderfarm.dto.response.GetTaskHistoryResponseDto;
import com.example.renderfarm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    @Qualifier("taskServiceImpl")
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequestDto request) {
        taskService.createTask(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetAllTasksResponseDto>> getAllTasks() {
        List<GetAllTasksResponseDto> allTasks = taskService.getAllTasks();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}/history")
    public ResponseEntity<List<GetTaskHistoryResponseDto>> getTaskUpdatesHistory(@PathVariable Long taskId) {
        List<GetTaskHistoryResponseDto> updatedTasks = taskService.getTaskUpdatesHistory(taskId);
        return new ResponseEntity<>(updatedTasks, HttpStatus.OK);
    }
}
