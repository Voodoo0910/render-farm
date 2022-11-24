package com.example.renderfarm.dto.response;

import com.example.renderfarm.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetTaskHistoryResponseDto implements Serializable {
    private LocalDateTime updatedAt;
    private TaskStatus afterUpdatingStatus;
    private TaskStatus beforeUpdatingStatus;
    private LocalDateTime taskCreatedAt;
}
