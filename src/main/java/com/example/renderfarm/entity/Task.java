package com.example.renderfarm.entity;

import com.example.renderfarm.enums.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<TaskUpdateHistory> taskUpdateHistory = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;
}
