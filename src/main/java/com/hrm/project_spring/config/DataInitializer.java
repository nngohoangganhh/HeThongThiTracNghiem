//package com.hrm.project_spring.config;
//
//import com.hrm.project_spring.entity.Feature;
//import com.hrm.project_spring.entity.Permission;
//import com.hrm.project_spring.entity.Role;
//import com.hrm.project_spring.entity.User;
//import com.hrm.project_spring.repository.FeatureRepository;
//import com.hrm.project_spring.repository.PermissionRepository;
//import com.hrm.project_spring.repository.RoleRepository;
//import com.hrm.project_spring.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
///**
// * Tự động seed dữ liệu mặc định khi ứng dụng khởi động.
// * Chỉ tạo nếu chưa tồn tại (idempotent - chạy nhiều lần không bị lỗi).
// */
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DataInitializer implements CommandLineRunner {
//
//    private final FeatureRepository featureRepository;
//    private final PermissionRepository permissionRepository;
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void run(String... args) {
//        log.info("=== DataInitializer: Seeding default data ===");
//
//        // 1. Tạo Features
//        Feature userFeature      = createFeatureIfNotExists("USER",       "Quản lý người dùng");
//        Feature roleFeature      = createFeatureIfNotExists("ROLE",       "Quản lý vai trò");
//        Feature examFeature      = createFeatureIfNotExists("EXAM",       "Quản lý kỳ thi");
//        Feature testFeature      = createFeatureIfNotExists("TEST",       "Quản lý bài thi");
//        Feature featureFeature   = createFeatureIfNotExists("FEATURE",    "Quản lý tính năng");
//        Feature permFeature      = createFeatureIfNotExists("PERMISSION", "Quản lý phân quyền");
//
//        // 2. Tạo Permissions
//        // USER
//        Permission userRead    = createPermIfNotExists("USER:READ",    "READ",   "Xem người dùng",    userFeature);
//        Permission userCreate  = createPermIfNotExists("USER:CREATE",  "CREATE", "Tạo người dùng",    userFeature);
//        Permission userUpdate  = createPermIfNotExists("USER:UPDATE",  "UPDATE", "Sửa người dùng",    userFeature);
//        Permission userDelete  = createPermIfNotExists("USER:DELETE",  "DELETE", "Xóa người dùng",    userFeature);
//
//        // ROLE
//        Permission roleRead    = createPermIfNotExists("ROLE:READ",    "READ",   "Xem vai trò",       roleFeature);
//        Permission roleCreate  = createPermIfNotExists("ROLE:CREATE",  "CREATE", "Tạo vai trò",       roleFeature);
//        Permission roleUpdate  = createPermIfNotExists("ROLE:UPDATE",  "UPDATE", "Sửa vai trò",       roleFeature);
//        Permission roleDelete  = createPermIfNotExists("ROLE:DELETE",  "DELETE", "Xóa vai trò",       roleFeature);
//
//        // EXAM
//        Permission examRead    = createPermIfNotExists("EXAM:READ",    "READ",   "Xem kỳ thi",        examFeature);
//        Permission examCreate  = createPermIfNotExists("EXAM:CREATE",  "CREATE", "Tạo kỳ thi",        examFeature);
//        Permission examUpdate  = createPermIfNotExists("EXAM:UPDATE",  "UPDATE", "Sửa kỳ thi",        examFeature);
//        Permission examDelete  = createPermIfNotExists("EXAM:DELETE",  "DELETE", "Xóa kỳ thi",        examFeature);
//
//        // TEST
//        Permission testRead    = createPermIfNotExists("TEST:READ",    "READ",   "Xem bài thi",       testFeature);
//        Permission testCreate  = createPermIfNotExists("TEST:CREATE",  "CREATE", "Tạo bài thi",       testFeature);
//        Permission testUpdate  = createPermIfNotExists("TEST:UPDATE",  "UPDATE", "Sửa bài thi",       testFeature);
//        Permission testDelete  = createPermIfNotExists("TEST:DELETE",  "DELETE", "Xóa bài thi",       testFeature);
//
//        // FEATURE
//        Permission featRead    = createPermIfNotExists("FEATURE:READ",   "READ",   "Xem tính năng",   featureFeature);
//        Permission featCreate  = createPermIfNotExists("FEATURE:CREATE", "CREATE", "Tạo tính năng",   featureFeature);
//        Permission featUpdate  = createPermIfNotExists("FEATURE:UPDATE", "UPDATE", "Sửa tính năng",   featureFeature);
//        Permission featDelete  = createPermIfNotExists("FEATURE:DELETE", "DELETE", "Xóa tính năng",   featureFeature);
//
//        // PERMISSION
//        Permission permRead    = createPermIfNotExists("PERMISSION:READ",   "READ",   "Xem phân quyền",  permFeature);
//        Permission permCreate  = createPermIfNotExists("PERMISSION:CREATE", "CREATE", "Tạo phân quyền",  permFeature);
//        Permission permUpdate  = createPermIfNotExists("PERMISSION:UPDATE", "UPDATE", "Sửa phân quyền",  permFeature);
//        Permission permDelete  = createPermIfNotExists("PERMISSION:DELETE", "DELETE", "Xóa phân quyền",  permFeature);
//        Permission permAssign  = createPermIfNotExists("PERMISSION:ASSIGN", "ASSIGN", "Gán phân quyền",  permFeature);
//
//        // 3. Tạo Roles
//        // ADMIN: tất cả quyền
//        Set<Permission> adminPerms = new HashSet<>(Arrays.asList(
//            userRead, userCreate, userUpdate, userDelete,
//            roleRead, roleCreate, roleUpdate, roleDelete,
//            examRead, examCreate, examUpdate, examDelete,
//            testRead, testCreate, testUpdate, testDelete,
//            featRead, featCreate, featUpdate, featDelete,
//            permRead, permCreate, permUpdate, permDelete, permAssign
//        ));
//        Role adminRole = createRoleIfNotExists("ADMIN", "Quản trị viên", adminPerms);
//
//        // TEACHER: quản lý exam, test, xem user
//        Set<Permission> teacherPerms = new HashSet<>(Arrays.asList(
//            userRead,
//            examRead, examCreate, examUpdate,
//            testRead, testCreate, testUpdate
//        ));
//        Role teacherRole = createRoleIfNotExists("TEACHER", "Giáo viên", teacherPerms);
//
//        // STUDENT: chỉ xem
//        Set<Permission> studentPerms = new HashSet<>(Arrays.asList(
//            examRead,
//            testRead
//        ));
//        Role studentRole = createRoleIfNotExists("STUDENT", "Học sinh", studentPerms);
//
//        // 4. Tạo user admin mặc định
//        createAdminUserIfNotExists(adminRole);
//
//        log.info("=== DataInitializer: Done ===");
//    }
//
//    private Feature createFeatureIfNotExists(String code, String name) {
//        return featureRepository.findByCode(code).orElseGet(() -> {
//            Feature f = Feature.builder()
//                    .code(code)
//                    .name(name)
//                    .description("Tính năng " + name)
//                    .build();
//            log.info("  [+] Feature: {}", code);
//            return featureRepository.save(f);
//        });
//    }
//
//    private Permission createPermIfNotExists(String code, String action, String name, Feature feature) {
//        return permissionRepository.findByCode(code).orElseGet(() -> {
//            Permission p = Permission.builder()
//                    .code(code)
//                    .action(action)
//                    .name(name)
//                    .description("Quyền " + name)
//                    .feature(feature)
//                    .build();
//            log.info("  [+] Permission: {}", code);
//            return permissionRepository.save(p);
//        });
//    }
//
//    private Role createRoleIfNotExists(String code, String name, Set<Permission> permissions) {
//        return roleRepository.findByCode(code).orElseGet(() -> {
//            Role r = Role.builder()
//                    .code(code)
//                    .name(name)
//                    .description("Vai trò " + name)
//                    .permissions(permissions)
//                    .build();
//            log.info("  [+] Role: {} ({} permissions)", code, permissions.size());
//            return roleRepository.save(r);
//        });
//    }
//
//    private void createAdminUserIfNotExists(Role adminRole) {
//        if (!userRepository.existsByUsername("admin")) {
//            User admin = User.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("Admin@123"))
//                    .email("admin@hrm.com")
//                    .fullName("Super Admin")
//                    .status("ACTIVE")
//                    .roles(new HashSet<>(Collections.singleton(adminRole)))
//                    .build();
//            userRepository.save(admin);
//            log.info("  [+] Default admin user created: admin / Admin@123");
//        }
//    }
//}
