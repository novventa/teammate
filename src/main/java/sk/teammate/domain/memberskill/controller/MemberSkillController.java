package sk.teammate.domain.memberskill.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.memberskill.dto.MemberSkillCreateRequest;
import sk.teammate.domain.memberskill.dto.MemberSkillResponse;
import sk.teammate.domain.memberskill.service.MemberSkillService;

import java.util.List;

@RestController
@RequestMapping("/api/members/{memberId}/skills")
@RequiredArgsConstructor
public class MemberSkillController {

    private final MemberSkillService memberSkillService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<MemberSkillResponse>> getMemberSkills(@PathVariable String memberId) {
        return ResponseEntity.ok(memberSkillService.getSkillsByMember(memberId));
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> addSkill(
            @PathVariable String memberId,
            @RequestBody @Valid MemberSkillCreateRequest request
    ) {
        memberSkillService.addSkillToMember(memberId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberSkillId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> removeSkill(@PathVariable Long memberSkillId) {
        memberSkillService.removeSkill(memberSkillId);
        return ResponseEntity.noContent().build();
    }
}
