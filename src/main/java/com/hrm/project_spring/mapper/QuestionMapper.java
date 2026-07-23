package com.hrm.project_spring.mapper;

import com.hrm.project_spring.dto.question.QuestionDetailResponse;
import com.hrm.project_spring.dto.question.QuestionResponse;
import com.hrm.project_spring.entity.Question;

public class QuestionMapper {
    public static QuestionResponse toResponse(Question question) {
        if (question == null) return null;
        return QuestionResponse.builder()
                .id(question.getId())
                .stem(question.getStem())
                .status(question.getStatus())
                .type(question.getType())
                .bloomLevel(question.getBloomLevel())
                .chapterName(question.getChapter().getName())
                .subjectName(question.getSubject().getName())
                .createdAt(question.getCreatedAt())
                .build();
    }
    public static QuestionDetailResponse toMapperResponse(Question question) {
        if (question == null) return null;
        return QuestionDetailResponse.builder()
                .id(question.getId())
                .stem(question.getStem())
                .status(question.getStatus())
                .type(question.getType())
                .bloomLevel(question.getBloomLevel())
                .score(question.getScore())
                .explanation(question.getExplanation())

                .build();

    }
}
