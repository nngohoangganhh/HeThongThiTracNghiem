# Audit Dự Án — HeThongThiTracNghiem Backend

## Tổng quan kiến trúc

```
Exam (kỳ thi) → Test (đề thi) → Question (câu hỏi) → Answer (đáp án)
     ↓                ↓
  Students       ExamAttempt (lần làm bài) → StudentAnswer (câu trả lời của sv)
```

---

## ✅ Những phần đã hoàn chỉnh

### 🔐 Auth
| Thành phần | Trạng thái |
|---|---|
| `AuthController` — login, register | ✅ |
| `AuthService` — JWT generation | ✅ |
| `SecurityConfig` — filter chain, JWT | ✅ |
| `DataInitializer` — seed roles, permissions, admin | ✅ |

### 👤 User
| Thành phần | Trạng thái |
|---|---|
| `UserController` — CRUD | ✅ |
| `UserService` — CRUD + assign role | ✅ |

### 🔑 RBAC (Feature → Permission → Role)
| Thành phần | Trạng thái |
|---|---|
| `FeatureController/Service` | ✅ |
| `PermissionController/Service` | ✅ |
| `RoleController/Service` | ✅ |

### 📋 Exam (Kỳ thi)
| Thành phần | Trạng thái |
|---|---|
| `ExamController` — CRUD | ✅ |
| `ExamService` — CRUD | ✅ |
| Gán/gỡ student vào exam | ✅ `POST /api/exams/{examId}/students` |

### 📝 Test (Đề thi)
| Thành phần | Trạng thái |
|---|---|
| `TestController` — CRUD | ✅ |
| `TestService` — CRUD | ✅ |
| Gán câu hỏi vào test (bulk) | ✅ `PUT /api/tests/{testId}/questions` *(vừa thêm)* |

### ❓ Question (Câu hỏi)
| Thành phần | Trạng thái |
|---|---|
| `QuestionController` — CRUD | ✅ |
| `QuestionService` — CRUD | ✅ |

### 💬 Answer (Đáp án)
| Thành phần | Trạng thái |
|---|---|
| `AnswerController` — CRUD + bulk | ✅ |
| `AnswerService` — CRUD + bulk | ✅ |

### 🎯 Luồng thi
| Thành phần | Trạng thái |
|---|---|
| `ExamAttemptController` — start/submit | ✅ |
| `ExamAttemptService` — logic chấm điểm | ✅ |

### 📊 Kết quả / Lịch sử thi
| Thành phần | Trạng thái |
|---|---|
| `ExamResultController` — xem lịch sử, review | ✅ |
| `ExamResultService` — query theo user/test/exam | ✅ |

---

## ❌ Vấn đề cần xử lý

### 🔴 Nghiêm trọng — Conflict Route

`ExamResultController` và `ExamAttemptController` **cùng map vào `/api/attempts`** → Spring sẽ báo lỗi khởi động hoặc request bị route sai.

```java
ExamAttemptController  → @RequestMapping("/api/attempts")  ← POST /start, POST /{id}/submit
ExamResultController   → @RequestMapping("/api/attempts")  ← GET /my, GET /test/{id}, ...
```

**Fix:** Đổi `ExamResultController` sang `@RequestMapping("/api/results")`.

---

### 🟡 Dead code — Nên xóa

| File | Lý do |
|---|---|
| `entity/Attempt.java` <br> `entity/ExamResult.java` | Entity cũ, bảng cũ (`attempts`, `exam_results`), không được dùng ở đâu |
| `repository/AttemptRespository.java` | Interface rỗng, không extend JpaRepository |
| `repository/ExamResultRespository.java` | Interface rỗng, không extend JpaRepository |
| `service/AttemptService.java` | Bị thay thế hoàn toàn bởi `ExamResultService` |
| `controller/AttemptController.java` | Bị thay thế bởi `ExamResultController` |

---

### 🟡 TestResponse thiếu trường questions

`TestResponse` hiện không trả về danh sách câu hỏi, nên sau khi gọi `PUT /api/tests/{testId}/questions` không thấy kết quả gán.

**Fix:** Thêm `int questionCount` vào `TestResponse`.

---

### 🟡 QuestionResponse thiếu danh sách answers

Khi gọi `GET /api/questions/{id}`, response không kèm danh sách đáp án → Student không biết đáp án nào để chọn khi thi.

**Fix:** Thêm `List<AnswerResponse> answers` vào `QuestionResponse` (ẩn `isCorrect` với student).

---

### 🟠 Luồng thi thiếu API lấy câu hỏi của test

Student sau khi `startAttempt` không có API để lấy danh sách câu hỏi của test đang thi.

**Fix cần:** `GET /api/tests/{testId}/questions` — trả về câu hỏi + đáp án (ẩn `isCorrect`).

---

### 🟠 Không có API profile cho student

`UserController` chỉ có Admin CRUD. Student không có cách tự xem thông tin hoặc đổi mật khẩu.

**Fix cần:** `GET /api/me`, `PUT /api/me/password`.

---

## 📋 Danh sách việc cần làm (ưu tiên)

| # | Việc cần làm | Độ ưu tiên |
|---|---|---|
| 1 | Đổi route `ExamResultController` → `/api/results` | 🔴 Cao nhất |
| 2 | Xóa dead code (5 file cũ không dùng) | 🟡 Cao |
| 3 | Thêm `questionCount` vào `TestResponse` | 🟡 Cao |
| 4 | Thêm `answers` vào `QuestionResponse` | 🟡 Cao |
| 5 | Thêm `GET /api/tests/{testId}/questions` cho student | 🟠 Trung bình |
| 6 | Thêm `GET /api/me` và `PUT /api/me/password` | 🟠 Trung bình |
