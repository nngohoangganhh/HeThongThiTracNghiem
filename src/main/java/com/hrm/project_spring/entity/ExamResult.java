package com.hrm.project_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_results")
public class ExamResult {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @ManyToOne
      @JoinColumn(name = "Students_id", nullable = false)
      private User user;
      @ManyToOne
      @JoinColumn(name = " tests_id", nullable = false)
      private Test test;
      @Column(name = "start_time")
      private LocalDate startTime;
      @Column(name = "end_time")
      private LocalDate endTime;
      private String status;

}
