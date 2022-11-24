package com.example.renderfarm.service.impl;

import com.example.renderfarm.dto.request.CreateTaskRequestDto;
import com.example.renderfarm.dto.response.GetAllTasksResponseDto;
import com.example.renderfarm.dto.response.GetTaskHistoryResponseDto;
import com.example.renderfarm.entity.AppUser;
import com.example.renderfarm.entity.Task;
import com.example.renderfarm.entity.TaskUpdateHistory;
import com.example.renderfarm.exception.custom.TaskNotFoundException;
import com.example.renderfarm.repository.AppUserRepository;
import com.example.renderfarm.repository.TaskRepository;
import com.example.renderfarm.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.example.renderfarm.constant.Constants.TASK_NOT_FOUND_MSG;
import static com.example.renderfarm.enums.TaskStatus.COMPLETE;
import static com.example.renderfarm.enums.TaskStatus.RENDERING;

@RequiredArgsConstructor
@Service("taskServiceImpl")
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public void createTask(CreateTaskRequestDto createTaskRequestDto) {
        AppUser principal = (AppUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Task task = new Task();
        task.setTaskStatus(RENDERING);
        task.setTitle(createTaskRequestDto.getTitle());
        task.setDescription(createTaskRequestDto.getDescription());
        task.setCreatedAt(LocalDateTime.now());
        task.setAppUser(appUserRepository.findFirstByUsername(principal.getUsername())
                .orElseThrow());

        taskRepository.save(task);
        CompletableFuture.runAsync(() -> changeStatus(task));
    }

    @Override
    public List<GetTaskHistoryResponseDto> getTaskUpdatesHistory(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MSG, taskId)))
                .getTaskUpdateHistory()
                .stream()
                .map(taskUpdateHistory -> new GetTaskHistoryResponseDto(
                        taskUpdateHistory.getUpdatedAt(),
                        taskUpdateHistory.getBeforeUpdateStatus(),
                        RENDERING,
                        taskUpdateHistory.getTaskCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetAllTasksResponseDto> getAllTasks() {
        AppUser user = (AppUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return taskRepository.findAllByAppUser_Username(user.getUsername())
                .stream()
                .map(entity -> new GetAllTasksResponseDto(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getDescription(),
                        entity.getTaskStatus()
                )).collect(Collectors.toList());
    }


    @SneakyThrows
    private void changeStatus(Task task) {
        Random random = new Random();
        int minutes = random.nextInt(1, 5) * 60000;
        Thread.sleep(minutes);
        task.setTaskStatus(COMPLETE);

        TaskUpdateHistory taskUpdateHistory = new TaskUpdateHistory();
        taskUpdateHistory.setBeforeUpdateStatus(RENDERING);
        taskUpdateHistory.setId(task.getId());
        taskUpdateHistory.setUpdatedAt(LocalDateTime.now());
        taskUpdateHistory.setTask(task);
        taskUpdateHistory.setTaskCreatedAt(task.getCreatedAt());
        taskUpdateHistory.setAfterUpdatingStatus(task.getTaskStatus());

        task.getTaskUpdateHistory().add(taskUpdateHistory);

        taskRepository.save(task);
    }
}
