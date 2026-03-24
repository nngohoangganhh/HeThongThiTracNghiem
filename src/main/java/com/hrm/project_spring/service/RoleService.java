package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.role.RoleRequest;
import com.hrm.project_spring.dto.role.RoleResponse;
import com.hrm.project_spring.entity.Role;
import com.hrm.project_spring.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;
    
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    // ALL
    public PageResponse<RoleResponse> getAllRoles(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Page<Role> page = roleRepository.findAll(pageable);
    List<RoleResponse> data = page.getContent().stream()
            .map(this::mapToResponse)
            .toList();
    return PageResponse.<RoleResponse>builder()
            .content(data)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();

    }
    // by id
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Role not found"));
        return mapToResponse(role);
    }
    // create
    public RoleResponse createRole(RoleRequest request) {
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Role savedRole = roleRepository.save(role);
        return mapToResponse(savedRole);
    }
    //update
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Role not found"));
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        Role updatedRole = roleRepository.save(role);
        return mapToResponse(updatedRole);
    }
    // delete
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Role not found"));
        roleRepository.delete(role);
    }

    private RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
