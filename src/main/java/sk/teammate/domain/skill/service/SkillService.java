package sk.teammate.domain.skill.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.teammate.domain.skill.dto.SkillCreateRequest;
import sk.teammate.domain.skill.dto.SkillResponse;
import sk.teammate.domain.skill.repository.SkillRepository;
import sk.teammate.entity.Skill;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<SkillResponse> getAll() {
        return skillRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(SkillResponse::from)
                .toList();
    }

    @Transactional
    public void create(SkillCreateRequest request) {
        if (skillRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("이미 존재하는 스킬입니다.");
        }
        Skill skill = Skill.builder()
                .name(request.name())
                .build();
        skillRepository.save(skill);
    }

    @Transactional
    public void delete(Long id) {
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("스킬을 찾을 수 없습니다."));
        skill.markDeleted();
    }
}