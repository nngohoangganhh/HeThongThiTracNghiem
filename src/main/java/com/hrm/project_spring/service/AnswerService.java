package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.answer.AnswerRequest;
import com.hrm.project_spring.dto.answer.AnswerResponse;
import com.hrm.project_spring.entity.Answer;
import com.hrm.project_spring.entity.Question;
import com.hrm.project_spring.repository.AnswerRepository;
import com.hrm.project_spring.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAnswersByQuestionId(Long questionId, boolean includeIsCorrect) {
        return answerRepository.findByQuestionId(questionId)
                .stream()
                .map(answer -> mapToResponse(answer, includeIsCorrect))
                .collect(Collectors.toList());
    }

    @Transactional
    public AnswerResponse addAnswerToQuestion(Long questionId, AnswerRequest request) {
        Question question = getQuestionById(questionId);
        if (" duy nhất".equalsIgnoreCase(question.getQuestionType()) && request.getIsCorrect()) {
            boolean hasCorrectAnswer = question.getAnswers().stream().anyMatch(Answer::getIsCorrect);
            if (hasCorrectAnswer) {
                throw new IllegalArgumentException("Câu hỏi trắc nghiệm chỉ có một đáp án đúng.");
            }
        }
        Answer answer = Answer.builder()
                .content(request.getContent())
                .isCorrect(request.getIsCorrect())
                .build();
        question.addAnswer(answer);
        Answer savedAnswer = answerRepository.save(answer);
        return mapToResponse(savedAnswer, true);
    }
    
    @Transactional
    public List<AnswerResponse> addBulkAnswers(Long questionId, List<AnswerRequest> requests) {
        Question question = getQuestionById(questionId);
        if ("duy nhất".equalsIgnoreCase(question.getQuestionType())) {
            long correctCount = requests.stream().filter(AnswerRequest::getIsCorrect).count();
            long currentCorrectCount = question.getAnswers().stream().filter(Answer::getIsCorrect).count();
            if (correctCount + currentCorrectCount > 1) {
                throw new IllegalArgumentException("Câu hỏi trắc nghiệm chỉ có một đáp án đúng.");
            }
        }
        List<Answer> newAnswers = requests.stream()
                .map(req -> {
                    Answer ans = Answer.builder()
                            .content(req.getContent())
                            .isCorrect(req.getIsCorrect())
                            .build();
                    question.addAnswer(ans);
                    return ans;
                })
                .collect(Collectors.toList());

        List<Answer> savedAnswers = answerRepository.saveAll(newAnswers);
        return savedAnswers.stream().map(a -> mapToResponse(a, true)).collect(Collectors.toList());
    }

    @Transactional
    public AnswerResponse updateAnswer(Long questionId, Long answerId,AnswerRequest request) {
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(() -> new RuntimeException(" Không tìm thấy câu trả lời cho câu hỏi "));
        Question question = answer.getQuestion();

        if ("single".equalsIgnoreCase(question.getQuestionType()) && request.getIsCorrect() && !Boolean.TRUE.equals(answer.getIsCorrect())) {
            boolean hasOtherCorrectAnswer = question.getAnswers().stream()
                    .anyMatch(a -> a.getIsCorrect() && !a.getId().equals(answerId));
            if (hasOtherCorrectAnswer) {
                throw new IllegalArgumentException("Câu hỏi trắc nghiệm chỉ có một đáp án đúng.");
            }
        }
        answer.setContent(request.getContent());
        answer.setIsCorrect(request.getIsCorrect());

        return mapToResponse(answerRepository.save(answer), true);
    }

    @Transactional
    public void deleteAnswer(Long questionId, Long answerId) {
        Answer answer = answerRepository.findByIdAndQuestionId(answerId, questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"không tìm thay câu trả lời"));
        
        answer.getQuestion().removeAnswer(answer);
        answerRepository.delete(answer);
    }

    private Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
    }

    private AnswerResponse mapToResponse(Answer answer, boolean includeIsCorrect) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .isCorrect(includeIsCorrect ? answer.getIsCorrect() : null) 
                .build();
    }
}
