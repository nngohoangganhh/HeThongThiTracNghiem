-- ============================================================
-- RBAC DATA INIT SCRIPT cho Hệ Thống Thi Trắc Nghiệm
-- Database: PostgreSQL
-- Mô tả: Tạo đầy đủ Features, Permissions, Roles và gán quyền
-- ============================================================

-- ======================== FEATURES ========================
INSERT INTO features (code, name, description) VALUES
  ('USER',       'Người dùng',    'Quản lý tài khoản người dùng'),
  ('EXAM',       'Kỳ thi',        'Quản lý kỳ thi và thi cử'),
  ('TEST',       'Bài kiểm tra',  'Quản lý bài kiểm tra (test)'),
  ('QUESTION',   'Câu hỏi',       'Quản lý ngân hàng câu hỏi'),
  ('ANSWER',     'Đáp án',        'Quản lý đáp án câu hỏi'),
  ('ROLE',       'Vai trò',       'Quản lý roles trong hệ thống'),
  ('PERMISSION', 'Quyền hạn',     'Quản lý permissions'),
  ('FEATURE',    'Tính năng',     'Quản lý features'),
  ('DASHBOARD',  'Bảng điều khiển','Xem thống kê và dashboard')
ON CONFLICT (code) DO NOTHING;

-- ======================== PERMISSIONS ========================
-- USER permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('USER:READ',   'READ',   'Xem người dùng',    'Xem danh sách và chi tiết người dùng',   (SELECT id FROM features WHERE code='USER')),
  ('USER:CREATE', 'CREATE', 'Tạo người dùng',    'Tạo tài khoản người dùng mới',            (SELECT id FROM features WHERE code='USER')),
  ('USER:UPDATE', 'UPDATE', 'Sửa người dùng',    'Cập nhật thông tin người dùng',           (SELECT id FROM features WHERE code='USER')),
  ('USER:DELETE', 'DELETE', 'Xóa người dùng',    'Xóa tài khoản người dùng',               (SELECT id FROM features WHERE code='USER'))
ON CONFLICT (code) DO NOTHING;

-- EXAM permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('EXAM:READ',   'READ',   'Xem kỳ thi',        'Xem danh sách và chi tiết kỳ thi',        (SELECT id FROM features WHERE code='EXAM')),
  ('EXAM:CREATE', 'CREATE', 'Tạo kỳ thi',        'Tạo kỳ thi mới',                         (SELECT id FROM features WHERE code='EXAM')),
  ('EXAM:UPDATE', 'UPDATE', 'Sửa kỳ thi',        'Cập nhật thông tin kỳ thi',              (SELECT id FROM features WHERE code='EXAM')),
  ('EXAM:DELETE', 'DELETE', 'Xóa kỳ thi',        'Xóa kỳ thi',                             (SELECT id FROM features WHERE code='EXAM')),
  ('EXAM:START',  'START',  'Bắt đầu thi',       'Học sinh bắt đầu làm bài thi',           (SELECT id FROM features WHERE code='EXAM')),
  ('EXAM:SUBMIT', 'SUBMIT', 'Nộp bài thi',       'Học sinh nộp bài thi',                   (SELECT id FROM features WHERE code='EXAM'))
ON CONFLICT (code) DO NOTHING;

-- TEST permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('TEST:READ',   'READ',   'Xem bài test',      'Xem danh sách và chi tiết bài test',      (SELECT id FROM features WHERE code='TEST')),
  ('TEST:CREATE', 'CREATE', 'Tạo bài test',      'Tạo bài test mới',                        (SELECT id FROM features WHERE code='TEST')),
  ('TEST:UPDATE', 'UPDATE', 'Sửa bài test',      'Cập nhật bài test, gán câu hỏi',         (SELECT id FROM features WHERE code='TEST')),
  ('TEST:DELETE', 'DELETE', 'Xóa bài test',      'Xóa bài test',                            (SELECT id FROM features WHERE code='TEST'))
ON CONFLICT (code) DO NOTHING;

-- QUESTION permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('QUESTION:READ',   'READ',   'Xem câu hỏi',   'Xem danh sách và chi tiết câu hỏi',      (SELECT id FROM features WHERE code='QUESTION')),
  ('QUESTION:CREATE', 'CREATE', 'Tạo câu hỏi',   'Tạo câu hỏi mới',                        (SELECT id FROM features WHERE code='QUESTION')),
  ('QUESTION:UPDATE', 'UPDATE', 'Sửa câu hỏi',   'Cập nhật câu hỏi',                       (SELECT id FROM features WHERE code='QUESTION')),
  ('QUESTION:DELETE', 'DELETE', 'Xóa câu hỏi',   'Xóa câu hỏi',                            (SELECT id FROM features WHERE code='QUESTION'))
ON CONFLICT (code) DO NOTHING;

-- ANSWER permissions  
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('ANSWER:READ',   'READ',   'Xem đáp án',      'Xem danh sách đáp án',                   (SELECT id FROM features WHERE code='ANSWER')),
  ('ANSWER:CREATE', 'CREATE', 'Tạo đáp án',      'Thêm đáp án cho câu hỏi',               (SELECT id FROM features WHERE code='ANSWER')),
  ('ANSWER:UPDATE', 'UPDATE', 'Sửa đáp án',      'Cập nhật đáp án',                        (SELECT id FROM features WHERE code='ANSWER')),
  ('ANSWER:DELETE', 'DELETE', 'Xóa đáp án',      'Xóa đáp án',                             (SELECT id FROM features WHERE code='ANSWER'))
ON CONFLICT (code) DO NOTHING;

-- ROLE permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('ROLE:READ',   'READ',   'Xem role',          'Xem danh sách roles',                    (SELECT id FROM features WHERE code='ROLE')),
  ('ROLE:CREATE', 'CREATE', 'Tạo role',          'Tạo role mới',                           (SELECT id FROM features WHERE code='ROLE')),
  ('ROLE:UPDATE', 'UPDATE', 'Sửa role',          'Cập nhật role',                          (SELECT id FROM features WHERE code='ROLE')),
  ('ROLE:DELETE', 'DELETE', 'Xóa role',          'Xóa role',                               (SELECT id FROM features WHERE code='ROLE'))
ON CONFLICT (code) DO NOTHING;

-- PERMISSION permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('PERMISSION:READ',   'READ',   'Xem permission',  'Xem danh sách permissions',          (SELECT id FROM features WHERE code='PERMISSION')),
  ('PERMISSION:CREATE', 'CREATE', 'Tạo permission',  'Tạo permission mới',                 (SELECT id FROM features WHERE code='PERMISSION')),
  ('PERMISSION:UPDATE', 'UPDATE', 'Sửa permission',  'Cập nhật permission',                (SELECT id FROM features WHERE code='PERMISSION')),
  ('PERMISSION:DELETE', 'DELETE', 'Xóa permission',  'Xóa permission',                     (SELECT id FROM features WHERE code='PERMISSION')),
  ('PERMISSION:ASSIGN', 'ASSIGN', 'Gán permission',  'Gán/bỏ permission cho role',         (SELECT id FROM features WHERE code='PERMISSION'))
ON CONFLICT (code) DO NOTHING;

-- FEATURE permissions
INSERT INTO permissions (code, action, name, description, feature_id) VALUES
  ('FEATURE:READ',   'READ',   'Xem feature',    'Xem danh sách features',                 (SELECT id FROM features WHERE code='FEATURE')),
  ('FEATURE:CREATE', 'CREATE', 'Tạo feature',    'Tạo feature mới',                        (SELECT id FROM features WHERE code='FEATURE')),
  ('FEATURE:UPDATE', 'UPDATE', 'Sửa feature',    'Cập nhật feature',                       (SELECT id FROM features WHERE code='FEATURE')),
  ('FEATURE:DELETE', 'DELETE', 'Xóa feature',    'Xóa feature',                            (SELECT id FROM features WHERE code='FEATURE'))
ON CONFLICT (code) DO NOTHING;

-- ======================== ROLES ========================
INSERT INTO roles (code, name, description) VALUES
  ('ADMIN',   'Quản trị viên', 'Có toàn quyền quản lý hệ thống'),
  ('STUDENT', 'Học sinh',      'Học sinh tham gia kỳ thi')
ON CONFLICT (code) DO NOTHING;

-- ======================== ADMIN: Gán TẤT CẢ permissions ========================
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

-- ======================== STUDENT: Gán đúng permissions cần thiết ========================
-- Phân tích từ @PreAuthorize trong các Controller:
--
-- ✅ EXAM:START  → POST /api/attempts/start/test/{testId}  (bắt đầu làm bài)
--                → GET  /api/dashboard/student              (xem dashboard cá nhân)
--                → GET  /api/dashboard/my-exams             (xem danh sách kỳ thi được gán)
--                → GET  /api/attempts/my                    (xem lịch sử thi)
--                → GET  /api/attempts/{attemptId}           (xem kết quả chi tiết)
--
-- ✅ EXAM:SUBMIT → POST /api/attempts/{attemptId}/submit    (nộp bài)
--
-- ✅ TEST:READ   → GET  /api/tests/{id}                     (lấy câu hỏi khi thi)
--                  [quan trọng: cần để ExamTaking load questions]
--
-- ✅ USER:READ   → GET  /api/auth/profile                   (xem profile - không cần vì không có @PreAuthorize)
--   [LƯU Ý: /api/auth/profile KHÔNG có @PreAuthorize → mọi authenticated user đều truy cập được]
--
-- ❌ KHÔNG CẦN:  USER:CREATE, USER:UPDATE, USER:DELETE
-- ❌ KHÔNG CẦN:  EXAM:CREATE, EXAM:UPDATE, EXAM:DELETE, EXAM:READ (admin features)
-- ❌ KHÔNG CẦN:  TEST:CREATE, TEST:UPDATE, TEST:DELETE
-- ❌ KHÔNG CẦN:  QUESTION:*, ANSWER:*, ROLE:*, PERMISSION:*, FEATURE:*

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'EXAM:START',   -- Bắt đầu thi + xem dashboard học sinh + lịch sử thi
    'EXAM:SUBMIT',  -- Nộp bài
    'TEST:READ'     -- Đọc câu hỏi của test khi đang thi
)
WHERE r.code = 'STUDENT'
ON CONFLICT DO NOTHING;

-- ============================================================
-- VERIFY: Kiểm tra sau khi chạy
-- ============================================================
-- SELECT r.code AS role, p.code AS permission
-- FROM roles r
-- JOIN role_permissions rp ON rp.role_id = r.id
-- JOIN permissions p ON p.id = rp.permission_id
-- WHERE r.code = 'STUDENT'
-- ORDER BY p.code;
