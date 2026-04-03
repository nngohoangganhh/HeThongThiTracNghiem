package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.feature.FeatureRequest;
import com.hrm.project_spring.dto.feature.FeatureResponse;
import com.hrm.project_spring.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @PreAuthorize("hasAuthority('FEATURE:READ')")
    @GetMapping
    public ResponseEntity<PageResponse<FeatureResponse>> getAllFeatures(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(featureService.getAllFeatures(pageNo, pageSize));
    }

    @PreAuthorize("hasAuthority('FEATURE:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> getFeatureById(@PathVariable Long id) {
        return ResponseEntity.ok(featureService.getFeatureById(id));
    }

    @PreAuthorize("hasAuthority('FEATURE:CREATE')")
    @PostMapping
    public ResponseEntity<FeatureResponse> createFeature(@RequestBody @Valid FeatureRequest request) {
        return ResponseEntity.ok(featureService.createFeature(request));
    }

    @PreAuthorize("hasAuthority('FEATURE:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<FeatureResponse> updateFeature(
            @PathVariable Long id,
            @RequestBody @Valid FeatureRequest request) {
        return ResponseEntity.ok(featureService.updateFeature(id, request));
    }

    @PreAuthorize("hasAuthority('FEATURE:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }
}
