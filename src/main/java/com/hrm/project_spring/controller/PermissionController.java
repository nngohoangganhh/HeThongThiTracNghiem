package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.permission.PermissionRequest;
import com.hrm.project_spring.dto.permission.PermissionResponse;
import com.hrm.project_spring.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PreAuthorize("hasAuthority('PERMISSION:READ')")
    @GetMapping
    public ResponseEntity<PageResponse<PermissionResponse>> getAllPermissions(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(permissionService.getAllPermissions(pageNo, pageSize));
    }

    @PreAuthorize("hasAuthority('PERMISSION:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @PreAuthorize("hasAuthority('PERMISSION:CREATE')")
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(permissionService.createPermission(request));
    }

    @PreAuthorize("hasAuthority('PERMISSION:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable Long id,
            @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(permissionService.updatePermission(id, request));
    }

    @PreAuthorize("hasAuthority('PERMISSION:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    // ======================== Gán/Gỡ permission vào Role ========================

    @PreAuthorize("hasAuthority('PERMISSION:ASSIGN')")
    @PostMapping("/roles/{roleId}/assign")
    public ResponseEntity<Void> assignPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {
        permissionService.assignPermissionsToRole(roleId, permissionIds);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('PERMISSION:ASSIGN')")
    @DeleteMapping("/roles/{roleId}/remove")
    public ResponseEntity<Void> removePermissionsFromRole(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {
        permissionService.removePermissionsFromRole(roleId, permissionIds);
        return ResponseEntity.ok().build();
    }
}
