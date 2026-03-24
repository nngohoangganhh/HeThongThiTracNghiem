package com.hrm.project_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exams")

public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
    name = "exam_students",
    joinColumns = @JoinColumn(name = "exam_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> students = new ArrayList<>();
    private String name;
    private String description;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    private String status;
    @Column(name = "created_at")
    private LocalTime createdAt;

}



