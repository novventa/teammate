package sk.teammate.domain.project.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.domain.project.dto.ProjectCreateRequest;
import sk.teammate.domain.project.dto.ProjectResponse;
import sk.teammate.domain.project.repository.ProjectRepository;
import sk.teammate.entity.Member;
import sk.teammate.entity.Project;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(String creatorId, ProjectCreateRequest request) {
        Member creator = memberRepository.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("생성자 정보를 찾을 수 없습니다."));

        Project project = Project.builder()
                .title(request.title())
                .description(request.description())
                .objectives(request.objectives())
                .budget(request.budget())
                .expectedDurationMonths(request.expectedDurationMonths())
                .createdBy(creator)
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public Page<ProjectResponse> getAll(Pageable pageable) {
        return projectRepository.findAllByDeletedAtIsNull(pageable)
                .map(ProjectResponse::from);
    }

    @Transactional
    public ProjectResponse getById(Long id) {
        return projectRepository.findByIdAndDeletedAtIsNull(id)
                .map(ProjectResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateStatus(Long id, Project.Status status) {
        Project project = projectRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));
        project.updateStatus(status);
    }
}
