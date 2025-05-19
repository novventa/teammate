package sk.teammate.domain.memberskill.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.domain.memberskill.dto.MemberSkillCreateRequest;
import sk.teammate.domain.memberskill.dto.MemberSkillResponse;
import sk.teammate.domain.memberskill.repository.MemberSkillRepository;
import sk.teammate.domain.skill.repository.SkillRepository;
import sk.teammate.entity.Member;
import sk.teammate.entity.MemberSkill;
import sk.teammate.entity.Skill;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSkillService {

    private final MemberRepository memberRepository;
    private final SkillRepository skillRepository;
    private final MemberSkillRepository memberSkillRepository;

    @Transactional
    public List<MemberSkillResponse> getSkillsByMember(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        return memberSkillRepository.findByMember(member).stream()
                .map(MemberSkillResponse::from)
                .toList();
    }

    @Transactional
    public void addSkillToMember(String memberId, MemberSkillCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        Skill skill = skillRepository.findById(request.skillId())
                .orElseThrow(() -> new EntityNotFoundException("스킬을 찾을 수 없습니다."));

        boolean alreadyExists = memberSkillRepository.existsByMemberAndSkill(member, skill);
        if (alreadyExists) {
            throw new IllegalArgumentException("이미 등록된 스킬입니다.");
        }

        MemberSkill memberSkill = MemberSkill.builder()
                .member(member)
                .skill(skill)
                .proficiencyLevel(request.proficiencyLevel())
                .yearsOfExperience(request.yearsOfExperience())
                .build();

        memberSkillRepository.save(memberSkill);
    }

    @Transactional
    public void removeSkill(Long memberSkillId) {
        MemberSkill skill = memberSkillRepository.findById(memberSkillId)
                .orElseThrow(() -> new EntityNotFoundException("스킬 정보를 찾을 수 없습니다."));
        skill.markDeleted();
    }
}
