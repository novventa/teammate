package sk.teammate.domain.member.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import sk.teammate.domain.member.dto.MemberCreateRequest;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<Member> getMembers(Member.MemberStatus status, Pageable pageable) {
        if (status != null) {
            return memberRepository.findAllByStatusAndDeletedAtIsNull(status, pageable);
        }
        return memberRepository.findAllByDeletedAtIsNull(pageable);
    }

    public Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사원을 찾을 수 없습니다."));
    }

    public void updateMember(String memberId, Member updated) {
        Member member = getMemberById(memberId);
        member.updateBio(updated.getBio());
        member.updatePosition(updated.getPosition());
        member.updateStatus(updated.getStatus());
    }

    public void createMember(MemberCreateRequest request) {
        if (memberRepository.existsById(request.id())) {
            throw new IllegalArgumentException("이미 존재하는 사번입니다.");
        }

        Member member = Member.builder()
                .id(request.id())
                .name(request.name())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .department(request.department())
                .position(request.position())
                .status(Member.MemberStatus.AVAILABLE)
                .joinDate(LocalDate.now())
                .bio(request.bio())
                .build();

        memberRepository.save(member);
    }

    public void updateLastLoginAt(String memberId) {
        Member member = getMemberById(memberId);
        member.updateLastLoginAt(LocalDateTime.now());
    }

    public void deleteMember(String memberId) {
        Member member = getMemberById(memberId);
        member.markDeleted(); // BaseEntity의 deletedAt 업데이트
    }
}