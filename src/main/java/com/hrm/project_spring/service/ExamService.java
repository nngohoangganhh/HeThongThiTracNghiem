package com.hrm.project_spring.service;


import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.exam.ExamRequest;
import com.hrm.project_spring.dto.exam.ExamResponse;
import com.hrm.project_spring.entity.Exam;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.repository.ExamRepository;
import com.hrm.project_spring.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public ExamService(ExamRepository examRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    //ALL và phân trang
    public PageResponse<ExamResponse> getAllExam(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Exam> page = examRepository.findAll(pageable);

        List<ExamResponse> data = page.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
        return PageResponse.<ExamResponse>builder()
                .content(data)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
    // tìm theo id
    public ExamResponse getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
        return mapToResponse(exam);
    }
    // tạo kỳ thi
    public ExamResponse create(ExamRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " thời gian bắt đầu phải trước thời gian kết thúc");
    }
    if(request.getName() == null || request.getName().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên kỳ thi không được để trống");
    }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String username = authentication.getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    Exam exam = Exam.builder()
            .name(request.getName())
            .description(request.getDescription())
            .startTime(request.getStartTime())
            .endTime(request.getEndTime())
            .status(request.getStatus())
            .createdBy(user)
            .createdAt(LocalTime.now())
            .build();
    exam = examRepository.save(exam);
    return mapToResponse(exam);
    }

    //Edit
    public ExamResponse update(Long id, ExamRequest request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " thời gian bắt đầu phải trước thời gian kết thúc");
        }
        if(request.getName() == null || request.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên kỳ thi không được để trống");
        }
        exam.setName(request.getName());
        exam.setDescription(request.getDescription());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        exam.setStatus(request.getStatus());

        Exam updatedExam = examRepository.save(exam);
        return mapToResponse(updatedExam);
    }
    //DELETE
    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, " KHÔNG TÌM THẤY ID");
        }
        examRepository.deleteById(id);
    }
    public ExamResponse mapToResponse(Exam exam) {
        User user = exam.getCreatedBy();
        return ExamResponse.builder()
                .id(exam.getId())
                .name(exam.getName())
                .description(exam.getDescription())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .createdById(user != null ? user.getId() : null)
                .createdByUsername(user != null ? user.getUsername() : null)
                .status(exam.getStatus())
                .createdAt(exam.getCreatedAt())
                .build();
    }

    // Gán student vào exam //
    public Set<User> getStudents(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy kỳ thi với ID: " + examId));
        return (Set<User>) exam.getStudents();
    }
    public ExamResponse assignStudentsToExam(Long examId, Set<Long> studentIds) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy kỳ thi với ID: " + examId));

        Set<User> students = userRepository.findAllById(studentIds).stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("STUDENT")))
                .collect(java.util.stream.Collectors.toSet());

        if (students.size() != studentIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều ID sinh viên không hợp lệ hoặc không phải là sinh viên");
        }
        exam.setStudents((List<User>) students);
        examRepository.save(exam);
        return null;
    }
    public void removeStudentFromExam(Long examId, Long studentId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy kỳ thi với ID: " + examId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Khong tim thay id cua hoc sinh" + studentId));
        if (!student.getRoles().stream().anyMatch(role -> role.getName().equals("STUDENT"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng với ID: " + studentId + " không phải là sinh viên");
        }
        exam.getStudents().remove(student);
        examRepository.save(exam);
    }











}
