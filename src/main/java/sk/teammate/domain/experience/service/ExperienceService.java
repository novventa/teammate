package sk.teammate.domain.experience.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.teammate.domain.experience.dto.ExperienceCreateRequest;
import sk.teammate.domain.experience.dto.ExperienceResponse;
import sk.teammate.domain.experience.repository.ExperienceRepository;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.entity.Experience;
import sk.teammate.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;

    @Transactional
    public List<ExperienceResponse> getByMember(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        return experienceRepository.findByMemberAndDeletedAtIsNull(member).stream()
                .map(ExperienceResponse::from)
                .toList();
    }

    @Transactional
    public void add(String memberId, ExperienceCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        Experience experience = Experience.builder()
                .member(member)
                .title(request.title())
                .companyName(request.companyName())
                .durationMonths(request.durationMonths())
                .summary(request.summary())
                .build();

        experienceRepository.save(experience);
    }

    @Transactional
    public void delete(Long experienceId) {
        Experience exp = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new EntityNotFoundException("경력을 찾을 수 없습니다."));
        exp.markDeleted();
    }
}
