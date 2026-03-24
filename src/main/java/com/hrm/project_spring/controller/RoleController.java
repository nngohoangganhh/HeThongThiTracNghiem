package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.role.RoleRequest;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.role.RoleResponse;
import com.hrm.project_spring.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/roles")
@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponse<RoleResponse>> getAllRoles(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(roleService.getAllRoles(pageNo,pageSize));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
