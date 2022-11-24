package com.example.renderfarm.dto.response;

import com.example.renderfarm.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAllTasksResponseDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}
