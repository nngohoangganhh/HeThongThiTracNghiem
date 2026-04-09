# 📮 Hướng Dẫn Test API Bằng Postman

> **Base URL:** `http://localhost:8080`  
> **Content-Type:** `application/json` (cho tất cả request có body)

---

## ⚙️ THIẾT LẬP BAN ĐẦU

### Bước 1: Tạo Collection & Environment

1. Mở Postman → **New Collection** → đặt tên `HeTongThiTracNghiem`
2. **New Environment** → đặt tên `Local` → thêm variable:
   | Variable | Initial Value |
   |---|---|
   | `base_url` | `http://localhost:8080` |
   | `token` | *(để trống - sẽ tự điền sau khi login)* |

3. Chọn environment `Local` ở góc trên phải Postman.

### Bước 2: Auto-save Token Sau Login

Ở tab **Tests** của request Login, dán đoạn script sau:
```javascript
var jsonData = pm.response.json();
if (jsonData.token) {
    pm.environment.set("token", jsonData.token);
    console.log("Token saved:", jsonData.token);
}
```

### Bước 3: Cấu hình Authorization dùng chung

Vào **Collection → Edit → Authorization**:
- Type: `Bearer Token`
- Token: `{{token}}`

> Tất cả request con sẽ tự kế thừa token này. Không cần set lại từng request.

---

## 🔐 MODULE 1: AUTH (Không cần token)

### 1.1 Register
```
POST {{base_url}}/api/auth/register
```
**Body (raw JSON):**
```json
{
  "username": "admin",
  "password": "Admin@123",
  "email": "admin@example.com",
  "fullName": "Super Admin",
  "roleIds": [],
  "permissionIds": []
}
```
> ⚠️ Lần đầu chưa có role → `roleIds: []`. Sau khi tạo role xong thì register user mới với roleIds.

---

### 1.2 Login ⭐ (Làm trước nhất)
```
POST {{base_url}}/api/auth/login
```
**Body (raw JSON):**
```json
{
  "username": "admin",
  "password": "Admin@123"
}
```
**Response trả về:**
```json
{
  "token": "eyJhbGciOi..."
}
```
> ✅ Token sẽ tự được lưu vào `{{token}}` nếu bạn đã thêm script ở tab Tests.

---

### 1.3 Get Profile (cần token)
```
GET {{base_url}}/api/auth/profile
```

---

### 1.4 Logout
```
POST {{base_url}}/api/auth/logout
```

---

## 👤 MODULE 2: USER (cần token ADMIN)

### 2.1 Lấy danh sách users (phân trang)
```
GET {{base_url}}/api/users?pageNo=0&pageSize=10
```

### 2.2 Lấy user theo ID
```
GET {{base_url}}/api/users/1
```

### 2.3 Tạo user mới
```
POST {{base_url}}/api/users
```
**Body:**
```json
{
  "username": "teacher01",
  "password": "Teacher@123",
  "email": "teacher01@example.com",
  "fullName": "Nguyen Van A",
  "roleIds": [2],
  "permissionIds": []
}
```

### 2.4 Cập nhật user
```
PUT {{base_url}}/api/users/2
```
**Body:** *(giống tạo mới, truyền các field cần cập nhật)*

### 2.5 Xóa user
```
DELETE {{base_url}}/api/users/2
```

---

## 🏷️ MODULE 3: FEATURE

> Phải tạo Feature trước khi tạo Permission!

### 3.1 Lấy danh sách features
```
GET {{base_url}}/api/features?pageNo=0&pageSize=20
```

### 3.2 Tạo Feature
```
POST {{base_url}}/api/features
```
**Body:**
```json
{
  "code": "EXAM",
  "name": "Quản lý kỳ thi",
  "description": "Tính năng quản lý kỳ thi"
}
```

> 🔁 Làm lại cho từng feature:
> - `USER` / `Quản lý người dùng`
> - `ROLE` / `Quản lý vai trò`
> - `EXAM` / `Quản lý kỳ thi`
> - `TEST` / `Quản lý bài thi`
> - `QUESTION` / `Quản lý câu hỏi`
> - `PERMISSION` / `Quản lý phân quyền`
> - `FEATURE` / `Quản lý tính năng`

### 3.3 Cập nhật Feature
```
PUT {{base_url}}/api/features/1
```

### 3.4 Xóa Feature
```
DELETE {{base_url}}/api/features/1
```

---

## 🔑 MODULE 4: PERMISSION

> Phải có Feature ID trước!

### 4.1 Lấy danh sách permissions
```
GET {{base_url}}/api/permissions?pageNo=0&pageSize=50
```

### 4.2 Tạo Permission
```
POST {{base_url}}/api/permissions
```
**Body:**
```json
{
  "code": "EXAM:READ",
  "action": "READ",
  "name": "Xem kỳ thi",
  "description": "Quyền xem danh sách kỳ thi",
  "featureId": 3
}
```

> 🔁 Tạo đủ các permission cần thiết. Ví dụ:
> ```json
> { "code": "EXAM:CREATE", "action": "CREATE", "name": "Tạo kỳ thi", "featureId": 3 }
> { "code": "EXAM:UPDATE", "action": "UPDATE", "name": "Sửa kỳ thi", "featureId": 3 }
> { "code": "EXAM:DELETE", "action": "DELETE", "name": "Xóa kỳ thi", "featureId": 3 }
> { "code": "USER:READ",   "action": "READ",   "name": "Xem users",   "featureId": 1 }
> ...
> ```

### 4.3 Cập nhật Permission
```
PUT {{base_url}}/api/permissions/1
```

### 4.4 Xóa Permission
```
DELETE {{base_url}}/api/permissions/1
```

---

## 🎭 MODULE 5: ROLE

### 5.1 Lấy danh sách roles
```
GET {{base_url}}/api/roles?pageNo=0&pageSize=10
```

### 5.2 Tạo Role ADMIN (với tất cả permissions)
```
POST {{base_url}}/api/roles
```
**Body:**
```json
{
  "code": "ADMIN",
  "name": "Quản trị viên",
  "description": "Có tất cả quyền",
  "permissionIds": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}
```

### 5.3 Tạo Role TEACHER
```
POST {{base_url}}/api/roles
```
**Body:**
```json
{
  "code": "TEACHER",
  "name": "Giáo viên",
  "description": "Quản lý exam, test, câu hỏi",
  "permissionIds": [1, 5, 6, 7, 9, 10, 11]
}
```

### 5.4 Tạo Role STUDENT
```
POST {{base_url}}/api/roles
```
**Body:**
```json
{
  "code": "STUDENT",
  "name": "Học sinh",
  "description": "Chỉ xem và thi",
  "permissionIds": [1, 5, 9]
}
```

### 5.5 Gán thêm Permission vào Role
```
POST {{base_url}}/api/roles/1/permissions
```
**Body:** *(array các permissionId)*
```json
[11, 12, 13]
```

### 5.6 Gỡ Permission khỏi Role
```
DELETE {{base_url}}/api/roles/1/permissions
```
**Body:**
```json
[13]
```

---

## 📅 MODULE 6: EXAM

### 6.1 Lấy danh sách kỳ thi
```
GET {{base_url}}/api/exams?pageNo=0&pageSize=10
```

### 6.2 Lấy chi tiết kỳ thi
```
GET {{base_url}}/api/exams/1
```

### 6.3 Tạo kỳ thi
```
POST {{base_url}}/api/exams
```
**Body:**
```json
{
  "name": "Kỳ thi Toán học kỳ 1",
  "description": "Bài thi cuối học kỳ 1 môn Toán",
  "startTime": "08:00:00",
  "endTime": "10:00:00",
  "status": "ACTIVE"
}
```
> ⚠️ Lưu ý: `startTime` / `endTime` hiện đang nhận `LocalTime` (chỉ giờ:phút:giây).

### 6.4 Cập nhật kỳ thi
```
PUT {{base_url}}/api/exams/1
```
**Body:** *(giống tạo kỳ thi)*

### 6.5 Xóa kỳ thi
```
DELETE {{base_url}}/api/exams/1
```

### 6.6 Xem danh sách student trong kỳ thi
```
GET {{base_url}}/api/exams/1/students
```

### 6.7 Gán student vào kỳ thi
```
POST {{base_url}}/api/exams/1/students
```
**Body:** *(array các userId của student)*
```json
[3, 4, 5]
```

### 6.8 Gỡ student khỏi kỳ thi
```
DELETE {{base_url}}/api/exams/1/students
```
**Body:**
```json
[5]
```

---

## 📝 MODULE 7: TEST (Bài thi trong Exam)

### 7.1 Lấy danh sách tests
```
GET {{base_url}}/api/tests?pageNo=0&pageSize=10
```

### 7.2 Lấy test theo ID
```
GET {{base_url}}/api/tests/1
```

### 7.3 Tạo test
```
POST {{base_url}}/api/tests
```
**Body:**
```json
{
  "examId": 1,
  "title": "Đề thi số 1",
  "durationMinutes": 60,
  "totalScore": 10
}
```

### 7.4 Cập nhật test
```
PUT {{base_url}}/api/tests/1
```

### 7.5 Xóa test
```
DELETE {{base_url}}/api/tests/1
```

---

## ❓ MODULE 8: QUESTION (Câu hỏi)

### 8.1 Lấy danh sách câu hỏi
```
GET {{base_url}}/api/questions?pageNo=0&pageSize=20
```

### 8.2 Lấy câu hỏi theo ID
```
GET {{base_url}}/api/questions/1
```

### 8.3 Tạo câu hỏi
```
POST {{base_url}}/api/questions
```
**Body:**
```json
{
  "content": "Kết quả của 2 + 2 bằng bao nhiêu?",
  "questionType": "MULTIPLE_CHOICE",
  "difficulty": "EASY"
}
```

### 8.4 Cập nhật câu hỏi
```
PUT {{base_url}}/api/questions/1
```
**Body:** *(giống tạo)*

### 8.5 Xóa câu hỏi
```
DELETE {{base_url}}/api/questions/1
```

---

## 💡 MODULE 9: ANSWER (Đáp án)

> Base path: `/api/questions/{questionId}/answers`

### 9.1 Xem đáp án của câu hỏi (chế độ admin - hiện isCorrect)
```
GET {{base_url}}/api/questions/1/answers?isExamTime=false
```

### 9.2 Xem đáp án khi đang thi (ẩn isCorrect)
```
GET {{base_url}}/api/questions/1/answers?isExamTime=true
```

### 9.3 Thêm 1 đáp án vào câu hỏi
```
POST {{base_url}}/api/questions/1/answers
```
**Body:**
```json
{
  "content": "4",
  "isCorrect": true
}
```

### 9.4 Thêm nhiều đáp án cùng lúc (Bulk) ⭐
```
POST {{base_url}}/api/questions/1/answers/bulk
```
**Body:**
```json
[
  { "content": "3",  "isCorrect": false },
  { "content": "4",  "isCorrect": true  },
  { "content": "5",  "isCorrect": false },
  { "content": "22", "isCorrect": false }
]
```

### 9.5 Cập nhật đáp án
```
PUT {{base_url}}/api/questions/1/answers/2
```
**Body:**
```json
{
  "content": "Đáp án đã sửa",
  "isCorrect": false
}
```

### 9.6 Xóa đáp án
```
DELETE {{base_url}}/api/questions/1/answers/2
```

---

## 🎯 MODULE 10: EXAM ATTEMPT (Luồng Thi)

> ⚠️ **Login bằng tài khoản STUDENT trước khi gọi các API này!**

### Thứ tự test đúng:

#### Bước 1: Student bắt đầu làm bài
```
POST {{base_url}}/api/attempts/start/test/1
```
*(Không cần body — lấy username từ JWT token)*

**Response trả về:**
```json
{
  "id": 1,
  "userId": 3,
  "testId": 1,
  "startTime": "2024-04-05T22:30:00",
  "submitTime": null,
  "score": null,
  "totalCorrect": null
}
```
> 📝 Ghi lại `id` của attempt (ví dụ: `1`) để dùng lúc nộp bài.

#### Bước 2: Student nộp bài
```
POST {{base_url}}/api/attempts/1/submit
```
**Body:**
```json
{
  "answers": [
    {
      "questionId": 1,
      "selectedAnswerId": 2
    },
    {
      "questionId": 2,
      "selectedAnswerId": 5
    },
    {
      "questionId": 3,
      "selectedAnswerId": null
    }
  ]
}
```
> - `questionId`: ID câu hỏi trong test
> - `selectedAnswerId`: ID đáp án student chọn (`null` = bỏ trống câu đó)

**Response trả về:**
```json
{
  "id": 1,
  "userId": 3,
  "testId": 1,
  "startTime": "2024-04-05T22:30:00",
  "submitTime": "2024-04-05T23:15:00",
  "score": 6.67,
  "totalCorrect": 2
}
```

---

## 🗺️ THỨ TỰ CHẠY TEST TOÀN BỘ HỆ THỐNG

```
1. Register user admin (không cần token)
2. Login admin → lấy token
3. Tạo Features (USER, ROLE, EXAM, TEST, QUESTION, PERMISSION, FEATURE)
4. Tạo Permissions cho từng feature (READ/CREATE/UPDATE/DELETE)
5. Tạo Roles (ADMIN, TEACHER, STUDENT) → gán permissions
6. Update user admin → gán roleId ADMIN
7. Đăng xuất → Login lại → token mới có đủ quyền
8. Tạo user TEACHER (roleIds: [TEACHER_ID])
9. Tạo user STUDENT (roleIds: [STUDENT_ID])
10. Tạo Exam
11. Gán student vào Exam
12. Tạo Test (thuộc Exam)
13. Tạo Questions
14. Thêm Answers cho từng Question (bulk)
15. Login bằng STUDENT account
16. Start Attempt (testId)
17. Submit Attempt (với câu trả lời)
18. Xem kết quả trong response
```

---

## 🛠️ Tips & Tricks

### Tip 1: Dùng Postman Variables
Sau khi tạo Exam, gán ngay ID vào variable:
```javascript
// Tab Tests của request Tạo Exam
var json = pm.response.json();
pm.environment.set("examId", json.id);
```
Rồi dùng `{{examId}}` trong URL thay vì gõ tay.

### Tip 2: Kiểm tra lỗi 403 Forbidden
Nếu gặp `403`, tức là user chưa có permission phù hợp:
1. Login bằng admin
2. Gán thêm permission cho role của user đó
3. Logout → Login lại để lấy token mới (có permission mới)

### Tip 3: Kiểm tra token hết hạn
Nếu gặp `401 Unauthorized`, token đã hết hạn → Login lại.

### Tip 4: DataInitializer đang bị comment
`DataInitializer.java` hiện bị comment out → không có dữ liệu mặc định.  
→ **Bạn phải tạo thủ công** theo thứ tự ở trên, hoặc nhờ tôi bật lại DataInitializer.

---

## 📊 Bảng Tóm Tắt Tất Cả API

| Method | URL | Mô tả | Permission |
|---|---|---|---|
| POST | `/api/auth/register` | Đăng ký | Public |
| POST | `/api/auth/login` | Đăng nhập | Public |
| POST | `/api/auth/logout` | Đăng xuất | Public |
| GET | `/api/auth/profile` | Xem profile | Authenticated |
| GET | `/api/users` | Danh sách users | USER:READ |
| GET | `/api/users/{id}` | Chi tiết user | USER:READ |
| POST | `/api/users` | Tạo user | USER:CREATE |
| PUT | `/api/users/{id}` | Sửa user | USER:UPDATE |
| DELETE | `/api/users/{id}` | Xóa user | USER:DELETE |
| GET | `/api/features` | Danh sách features | FEATURE:READ |
| POST | `/api/features` | Tạo feature | FEATURE:CREATE |
| PUT | `/api/features/{id}` | Sửa feature | FEATURE:UPDATE |
| DELETE | `/api/features/{id}` | Xóa feature | FEATURE:DELETE |
| GET | `/api/permissions` | Danh sách permissions | PERMISSION:READ |
| POST | `/api/permissions` | Tạo permission | PERMISSION:CREATE |
| PUT | `/api/permissions/{id}` | Sửa permission | PERMISSION:UPDATE |
| DELETE | `/api/permissions/{id}` | Xóa permission | PERMISSION:DELETE |
| GET | `/api/roles` | Danh sách roles | ROLE:READ |
| POST | `/api/roles` | Tạo role | ROLE:CREATE |
| PUT | `/api/roles/{id}` | Sửa role | ROLE:UPDATE |
| DELETE | `/api/roles/{id}` | Xóa role | ROLE:DELETE |
| POST | `/api/roles/{id}/permissions` | Gán permission | PERMISSION:ASSIGN |
| DELETE | `/api/roles/{id}/permissions` | Gỡ permission | PERMISSION:ASSIGN |
| GET | `/api/exams` | Danh sách kỳ thi | EXAM:READ |
| GET | `/api/exams/{id}` | Chi tiết kỳ thi | EXAM:READ |
| POST | `/api/exams` | Tạo kỳ thi | EXAM:CREATE |
| PUT | `/api/exams/{id}` | Sửa kỳ thi | EXAM:UPDATE |
| DELETE | `/api/exams/{id}` | Xóa kỳ thi | EXAM:DELETE |
| GET | `/api/exams/{id}/students` | Students trong exam | EXAM:READ |
| POST | `/api/exams/{id}/students` | Gán students | EXAM:UPDATE |
| DELETE | `/api/exams/{id}/students` | Gỡ students | EXAM:UPDATE |
| GET | `/api/tests` | Danh sách tests | TEST:READ |
| GET | `/api/tests/{id}` | Chi tiết test | TEST:READ |
| POST | `/api/tests` | Tạo test | TEST:CREATE |
| PUT | `/api/tests/{id}` | Sửa test | TEST:UPDATE |
| DELETE | `/api/tests/{id}` | Xóa test | TEST:DELETE |
| GET | `/api/questions` | Danh sách câu hỏi | QUESTION:READ |
| GET | `/api/questions/{id}` | Chi tiết câu hỏi | QUESTION:READ |
| POST | `/api/questions` | Tạo câu hỏi | QUESTION:CREATE |
| PUT | `/api/questions/{id}` | Sửa câu hỏi | QUESTION:UPDATE |
| DELETE | `/api/questions/{id}` | Xóa câu hỏi | QUESTION:DELETE |
| GET | `/api/questions/{id}/answers` | Xem đáp án | Public |
| POST | `/api/questions/{id}/answers` | Thêm đáp án | QUESTION:UPDATE |
| POST | `/api/questions/{id}/answers/bulk` | Thêm nhiều đáp án | QUESTION:UPDATE |
| PUT | `/api/questions/{id}/answers/{aid}` | Sửa đáp án | QUESTION:UPDATE |
| DELETE | `/api/questions/{id}/answers/{aid}` | Xóa đáp án | QUESTION:DELETE |
| POST | `/api/attempts/start/test/{testId}` | Bắt đầu thi | Authenticated |
| POST | `/api/attempts/{id}/submit` | Nộp bài | Authenticated |
