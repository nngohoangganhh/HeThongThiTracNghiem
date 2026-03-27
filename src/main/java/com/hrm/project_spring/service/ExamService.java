package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.exam.ExamListResponse;
import com.hrm.project_spring.dto.exam.ExamRequest;
import com.hrm.project_spring.dto.exam.ExamDetailResponse;
import com.hrm.project_spring.dto.student.StudentResponse;
import com.hrm.project_spring.entity.Exam;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.mapper.ExamMapper;
import com.hrm.project_spring.repository.ExamRepository;
import com.hrm.project_spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//    private final ExamRepository examRepository;
//    private final UserRepository userRepository;
//
//    public ExamService(ExamRepository examRepository, UserRepository userRepository) {
//        this.examRepository = examRepository;
//        this.userRepository = userRepository;
//    }
//
//    //ALL và phân trang
//    public PageResponse<ExamDetailResponse> getAllExam(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<Exam> page = examRepository.findAll(pageable);
//
//        List<ExamDetailResponse> data = page.getContent()
//                .stream()
//                .map(this::mapToResponse)
//                .toList();
//        return PageResponse.<ExamDetailResponse>builder()
//                .content(data)
//                .pageNo(page.getNumber())
//                .pageSize(page.getSize())
//                .totalElements(page.getTotalElements())
//                .totalPages(page.getTotalPages())
//                .last(page.isLast())
//                .build();
//    }
//    // tìm theo id
//    public ExamDetailResponse getExamById(Long id) {
//        Exam exam = examRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
//        return mapToResponse(exam);
//    }
//    // tạo kỳ thi
//    public ExamDetailResponse create(ExamRequest request) {
//        if (request.getStartTime().isAfter(request.getEndTime())) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " thời gian bắt đầu phải trước thời gian kết thúc");
//    }
//    if(request.getName() == null || request.getName().isEmpty()) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên kỳ thi không được để trống");
//    }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
//    Exam exam = Exam.builder()
//            .name(request.getName())
//            .description(request.getDescription())
//            .startTime(request.getStartTime())
//            .endTime(request.getEndTime())
//            .status(request.getStatus())
//            .createdBy(user)
//            .createdAt(LocalDateTime.now())
//            .build();
//    exam = examRepository.save(exam);
//    return mapToResponse(exam);
//    }
//    //Edit
//    public ExamDetailResponse update(Long id, ExamRequest request) {
//        Exam exam = examRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
//        if (request.getStartTime().isAfter(request.getEndTime())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " thời gian bắt đầu phải trước thời gian kết thúc");
//        }
//        if(request.getName() == null || request.getName().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên kỳ thi không được để trống");
//        }
//        exam.setName(request.getName());
//        exam.setDescription(request.getDescription());
//        exam.setStartTime(request.getStartTime());
//        exam.setEndTime(request.getEndTime());
//        exam.setStatus(request.getStatus());
//
//        Exam updatedExam = examRepository.save(exam);
//        return mapToResponse(updatedExam);
//    }
//    //DELETE
//    public void deleteExam(Long id) {
//        if (!examRepository.existsById(id)) {
//            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, " KHÔNG TÌM THẤY ID");
//        }
//        examRepository.deleteById(id);
//    }
//        // Lấy danh sách sinh viên đã được gán vào kỳ thi
//        public Set<StudentResponse> getStudents(Long examId) {
//        Exam exam  = examRepository.findById(examId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy kỳ thi với ID: " + examId));
//         return exam.getStudents().stream().map( user -> {
//                StudentResponse studentResponse = new StudentResponse();
//                studentResponse.setId(user.getId());
//                studentResponse.setUsername(user.getUsername());
//                return studentResponse;
//         }).collect(java.util.stream.Collectors.toSet());
//        }
//
//        // assign student vào exam
//        @Transactional
//       public ExamDetailResponse assignStudentsToExam(Long examId, Set<Long> studentIds) {
//        Exam exam = examRepository.findById(examId)
//            .orElseThrow(() -> new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Không tìm thấy kỳ thi với ID: " + examId
//            ));
//    Set<User> students = userRepository.findAllById(studentIds).stream()
//            .filter(user -> user.getRoles().stream()
//                    .anyMatch(role -> "STUDENT".equals(role.getName())))
//            .collect(Collectors.toSet());
//    if (students.size() != studentIds.size()) {
//        throw new ResponseStatusException(
//                HttpStatus.BAD_REQUEST,
//                "Một hoặc nhiều ID không hợp lệ hoặc không phải sinh viên"
//        );
//    }
//    // tránh duplicate
//    students.removeAll(exam.getStudents());
//    exam.getStudents().addAll(students);
//    examRepository.save(exam);
//    return mapToResponse(exam);
//}
//    // remove student khỏi exam
//    public void removeStudentFromExam(Long examId, Long studentId) {
//        Exam exam = examRepository.findById(examId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy kỳ thi với ID: " + examId));
//        User student = userRepository.findById(studentId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khong tim thay id cua hoc sinh" + studentId));
//        if (!student.getRoles().stream().anyMatch(role -> role.getName().equals("STUDENT"))) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng với ID: " + studentId + " không phải là sinh viên");
//        }
//        exam.getStudents().remove(student);
//        examRepository.save(exam);
//    }
//    public ExamDetailResponse mapToResponse(Exam exam) {
//        User user = exam.getCreatedBy();
//          Set<StudentResponse> students = exam.getStudents() != null
//            ? exam.getStudents().stream()
//                .map(u -> {
//                    StudentResponse s = new StudentResponse();
//                    s.setId(u.getId());
//                    s.setUsername(u.getUsername());
//                    return s;
//                })
//                .collect(Collectors.toSet())
//            : new HashSet<>();
//        return ExamDetailResponse.builder()
//                .id(exam.getId())
//                .name(exam.getName())
//                .description(exam.getDescription())
//                .startTime(exam.getStartTime())
//                .endTime(exam.getEndTime())
//                .createdById(user != null ? user.getId() : null)
//                .createdByUsername(user != null ? user.getUsername() : null)
//                .status(exam.getStatus())
//                .createdAt(exam.getCreatedAt())
//                .build();
//    }
//

@Service
@RequiredArgsConstructor
@Transactional
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    private static final String ROLE_STUDENT = "STUDENT";

    public PageResponse<ExamListResponse> getAllExam(int pageNo, int pageSize) {

        Page<Exam> page = examRepository.findAll(PageRequest.of(pageNo, pageSize));

        List<ExamListResponse> data = page.getContent()
                .stream()
                .map(ExamMapper::toListResponse)
                .toList();

        return PageResponse.<ExamListResponse>builder()
                .content(data)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    public ExamDetailResponse getExamById(Long id) {

        Exam exam = examRepository.findByIdWithStudents(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));

        return ExamMapper.toDetailResponse(exam);
    }

    public ExamDetailResponse create(ExamRequest request) {

        validate(request);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        Exam exam = Exam.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startTime(request.getStartTime().atDate(LocalDate.now()))
                .endTime(request.getEndTime().atDate(LocalDate.now()))
                .status(request.getStatus())
                .createdBy(user)
                .createdAt(LocalTime.now())
                .build();

        return ExamMapper.toDetailResponse(examRepository.save(exam));
    }

    public ExamDetailResponse update(Long id, ExamRequest request) {

        validate(request);

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));

        exam.setName(request.getName());
        exam.setDescription(request.getDescription());
        exam.setStartTime(request.getStartTime().atDate(LocalDate.from(LocalDateTime.now())));
        exam.setEndTime(request.getEndTime().atDate(LocalDate.from(LocalDateTime.now())));
        exam.setStatus(request.getStatus());

        return ExamMapper.toDetailResponse(examRepository.save(exam));
    }

    public void deleteExam(Long id) {

        if (!examRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found");
        }

        examRepository.deleteById(id);
    }

    public ExamDetailResponse assignStudentsToExam(Long examId, Set<Long> studentIds) {

        Exam exam = examRepository.findByIdWithStudents(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));

        Set<User> students = getValidStudents(studentIds);

        students.removeAll(exam.getStudents()); // tránh duplicate

        exam.getStudents().addAll(students);

        return ExamMapper.toDetailResponse(exam);
    }

    public ExamDetailResponse removeStudentsFromExam(Long examId, Set<Long> studentIds) {

        Exam exam = examRepository.findByIdWithStudents(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));

        exam.getStudents().removeIf(u -> studentIds.contains(u.getId()));

        return ExamMapper.toDetailResponse(exam);
    }

    public Set<StudentResponse> getStudentsByExamId(Long examId) {

        Exam exam = examRepository.findByIdWithStudents(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));

        return exam.getStudents().stream()
                .map(u -> StudentResponse.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<User> getValidStudents(Set<Long> ids) {

        Set<User> users = userRepository.findAllById(ids).stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> ROLE_STUDENT.equals(r.getName())))
                .collect(Collectors.toSet());

        if (users.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User không hợp lệ");
        }
        return users;
    }

    private void validate(ExamRequest request) {

        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start phải trước End");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên không được trống");
        }
    }
}
