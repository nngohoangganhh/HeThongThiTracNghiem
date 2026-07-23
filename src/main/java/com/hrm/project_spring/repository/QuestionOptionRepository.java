package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findByQuestionId(Long questionId);

    Optional<QuestionOption> findByIdAndQuestionId(Long answerId, Long questionId);
}
