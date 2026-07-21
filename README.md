<h1 align="center">🎓 Exam Online System</h1>

<p align="center">
A RESTful Online Examination System built with Spring Boot
</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

</p>

---

# 🚀 Overview

Exam Online System là hệ thống thi trắc nghiệm trực tuyến được xây dựng bằng **Java Spring Boot**, hỗ trợ quản lý người dùng, phân quyền, ngân hàng câu hỏi, bài thi và kết quả làm bài.

---

# ✨ Features

✅ JWT Authentication

✅ Role-Based Access Control (RBAC)

✅ User Management

✅ Exam Management

✅ Question Bank

✅ Online Exam

✅ Dashboard Statistics

✅ Swagger API

---

# 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| ☕ Language | Java 17 |
| 🌱 Framework | Spring Boot |
| 🔒 Security | Spring Security + JWT |
| 🗄 Database | PostgreSQL |
| 📦 ORM | Hibernate / JPA |
| 📚 API Docs | Swagger |
| 🚀 Deploy | Render |
| 🐳 Container | Docker |

---

# 🏗 Architecture

```text
                Client
                   │
            Spring Security
                   │
             REST Controller
                   │
              Service Layer
                   │
            Repository Layer
                   │
              PostgreSQL
```

---

# 📦 Modules

| Module | Description |
|---------|-------------|
| 🔐 Authentication | Login, JWT, Refresh Token |
| 👤 User | User Management |
| 👥 Role | Role Management |
| 🔑 Permission | RBAC |
| 📝 Question | Question Bank |
| 📚 Test | Test Management |
| 🎯 Exam | Exam Management |
| 🏫 Classroom | Classroom |
| 📊 Dashboard | Statistics |

---

# 🔒 RBAC Model

```text
User
   │
 Role
   │
Permission
   │
Feature
```

---

# 📖 API Documentation

```
http://localhost:8080/swagger-ui/index.html
```

---

# ⚙ Getting Started

### Clone project

```bash
git clone https://github.com/nngohoangganhh/ExamOnlineSystem.git
```

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

---

# 📁 Project Structure

```text
src
├── config
├── security
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── exception
└── util
```

---

# 📸 Screenshots

| Swagger | Database |
|----------|----------|
| *(Coming Soon)* | *(Coming Soon)* |

---

# 🚀 Deployment

Backend

```
https://hethongthitracnghiem-yb6s.onrender.com/swagger-ui/index.html#/)```
...



# 📌 Roadmap

- [x] JWT Authentication
- [x] RBAC Authorization
- [x] CRUD User
- [x] CRUD Exam
- [x] CRUD Question
- [ ] Docker Compose
- [ ] Unit Test
- [ ] GitHub Actions
- [ ] Redis Cache
- [ ] Email Notification

---

# 👨‍💻 Author

**Ngô Hoàng Anh**

⭐ Nếu project hữu ích, hãy cho một Star nhé.
