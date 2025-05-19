package sk.teammate.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.member.dto.MemberCreateRequest;
import sk.teammate.domain.member.dto.MemberListItem;
import sk.teammate.domain.member.dto.MemberResponse;
import sk.teammate.domain.member.dto.UpdateMemberRequest;
import sk.teammate.domain.member.service.MemberService;
import sk.teammate.entity.Member;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<Page<MemberListItem>> getAllMembers(
            @RequestParam(required = false) Member.MemberStatus status,
            @PageableDefault(size = 20, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(
                memberService.getMembers(status, pageable)
                        .map(MemberListItem::from)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String id) {
        return ResponseEntity.ok(
                MemberResponse.from(memberService.getMemberById(id))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> updateMember(
            @PathVariable String id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        Member updated = Member.builder()
                .position(request.position())
                .bio(request.bio())
                .status(request.status())
                .build();
        memberService.updateMember(id, updated);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequest request) {
        memberService.createMember(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}