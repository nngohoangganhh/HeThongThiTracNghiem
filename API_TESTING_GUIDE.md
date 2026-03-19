# Hướng dẫn Kiểm tra API (API Testing Guide)

Tài liệu này hướng dẫn cách kiểm tra các API của ứng dụng Spring Boot, bao gồm Xác thực (Authentication) và Quản lý Người dùng (User Management). Bạn có thể sử dụng **Postman**, **Insomnia** hoặc **cURL** để gọi các API này.

URL gốc của máy chủ nội bộ (mặc định): `http://localhost:8080` (hoặc cổng bạn đã cấu hình trong `application.yml`).

---

## 1. API Xác thực (`/api/auth`)
*Lưu ý: Các API đăng ký và đăng nhập không yêu cầu Access Token.*

### 1.1 Đăng ký tài khoản (Register)
* **URL:** `/api/auth/register`
* **Method:** `POST`
* **Body (JSON):**
    ```json
    {
        "username": "testuser",
        "password": "password123",
        "email": "testuser@example.com",
        "fullName": "Test User"
    }
    ```

### 1.2 Đăng nhập (Login)
* **URL:** `/api/auth/login`
* **Method:** `POST`
* **Body (JSON):**
    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```
* **Response:** (Bạn sẽ nhận được một `token`. Hãy copy giá trị này để dùng cho các API phía dưới)
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdW...",
        "message": "Login successful"
    }
    ```

---

## 🛑 Cấu hình Token (Dành cho các API bên dưới)
Tất cả các API từ phần này trở xuống đều yêu cầu có Token xác thực.
* **Header cần thêm vào:**
  * Key: `Authorization`
  * Value: `Bearer <token_ban_nhan_duoc_khi_login>`

*(Trong Postman: Mở tab **Authorization** -> Chọn Type là **Bearer Token** -> Dán Token vào ô Token)*

### 1.3 Lấy thông tin tài khoản đang đăng nhập (Profile)
* **URL:** `/api/auth/profile`
* **Method:** `GET`
* **Header:** `Authorization: Bearer <token>`

### 1.4 Đăng xuất (Logout)
* **URL:** `/api/auth/logout`
* **Method:** `POST`
* **Header:** `Authorization: Bearer <token>`

---

## 2. API Quản lý Người dùng (User CRUD - `/api/users`)
*Tất cả API này đều yêu cầu `Authorization: Bearer <token>` header.*

### 2.1 Lấy danh sách người dùng (Có phân trang)
* **URL:** `/api/users?pageNo=0&pageSize=10`
* **Method:** `GET`
* **Query Params:**
  * `pageNo` (Mặc định: 0) - Trang hiện tại (Bắt đầu từ 0)
  * `pageSize` (Mặc định: 10) - Số lượng trên mỗi trang
* **Header:** `Authorization: Bearer <token>`
* **Response Mẫu (PageResponse):**
    ```json
    {
        "content": [
            {
                "id": 1,
                "username": "testuser",
                "email": "testuser@example.com",
                "fullName": "Test User",
                "status": "ACTIVE",
                "createdAt": "2026-03-19T10:00:00"
            }
        ],
        "pageNo": 0,
        "pageSize": 10,
        "totalElements": 1,
        "totalPages": 1,
        "last": true
    }
    ```

### 2.2 Lấy thông tin một người dùng cụ thể (Theo ID)
* **URL:** `/api/users/{id}` *(Ví dụ: `/api/users/1`)*
* **Method:** `GET`
* **Header:** `Authorization: Bearer <token>`

### 2.3 Tạo mới một người dùng (Dành cho Admin/Quản lý)
* **URL:** `/api/users`
* **Method:** `POST`
* **Header:** `Authorization: Bearer <token>`
* **Body (JSON):**
    ```json
    {
        "username": "newuser",
        "password": "password123",
        "email": "newuser@example.com",
        "fullName": "New User"
    }
    ```

### 2.4 Cập nhật thông tin người dùng
* **URL:** `/api/users/{id}` *(Ví dụ: `/api/users/2`)*
* **Method:** `PUT`
* **Header:** `Authorization: Bearer <token>`
* **Body (JSON):**
    ```json
    {
        "username": "newuser_updated",
        "password": "newpassword123", 
        "email": "newuser@example.com",
        "fullName": "New User Updated"
    }
    ```
*(Lưu ý: Nếu không muốn đổi mật khẩu, bạn có thể truyền rỗng `""` hoặc không kèm mật khẩu tùy theo logic đã viết trong Service).*

### 2.5 Xóa người dùng
* **URL:** `/api/users/{id}` *(Ví dụ: `/api/users/2`)*
* **Method:** `DELETE`
* **Header:** `Authorization: Bearer <token>`
* **Response:** Trả về HTTP Status `204 No Content` nếu thành công.
