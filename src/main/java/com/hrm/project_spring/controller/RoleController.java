package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.permission.PermissionResponse;
import com.hrm.project_spring.dto.role.RoleRequest;
import com.hrm.project_spring.dto.role.RoleResponse;
import com.hrm.project_spring.service.PermissionService;
import com.hrm.project_spring.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    @PreAuthorize("hasAuthority('ROLE:READ')")
    @GetMapping
    public ResponseEntity<PageResponse<RoleResponse>> getAllRoles(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(roleService.getAllRoles(pageNo, pageSize));
    }

    @PreAuthorize("hasAuthority('ROLE:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @PreAuthorize("hasAuthority('ROLE:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // ======================== Gán/Gỡ Permission vào Role ========================

    @PreAuthorize("hasAuthority('PERMISSION:ASSIGN')")
    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<Void> assignPermissions(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {
        permissionService.assignPermissionsToRole(roleId, permissionIds);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('PERMISSION:ASSIGN')")
    @DeleteMapping("/{roleId}/permissions")
    public ResponseEntity<Void> removePermissions(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {
        permissionService.removePermissionsFromRole(roleId, permissionIds);
        return ResponseEntity.ok().build();
    }
}
