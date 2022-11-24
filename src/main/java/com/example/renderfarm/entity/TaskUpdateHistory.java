package com.example.renderfarm.entity;

import com.example.renderfarm.enums.TaskStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_status_journal")
@Data
public class TaskUpdateHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "after_update_status")
    @Enumerated(STRING)
    private TaskStatus afterUpdatingStatus;

    @Enumerated(STRING)
    @Column(name = "before_update_status")
    private TaskStatus beforeUpdateStatus;

    @Column(name = "task_created_at")
    private LocalDateTime taskCreatedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;
}
