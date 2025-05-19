package sk.teammate.domain.experience.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.experience.dto.ExperienceCreateRequest;
import sk.teammate.domain.experience.dto.ExperienceResponse;
import sk.teammate.domain.experience.service.ExperienceService;

import java.util.List;

@RestController
@RequestMapping("/api/members/{memberId}/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<ExperienceResponse>> getAll(@PathVariable String memberId) {
        return ResponseEntity.ok(experienceService.getByMember(memberId));
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> add(
            @PathVariable String memberId,
            @RequestBody @Valid ExperienceCreateRequest request
    ) {
        experienceService.add(memberId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{experienceId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> delete(@PathVariable Long experienceId) {
        experienceService.delete(experienceId);
        return ResponseEntity.noContent().build();
    }
}