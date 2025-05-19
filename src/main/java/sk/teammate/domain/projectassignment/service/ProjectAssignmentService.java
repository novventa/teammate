package sk.teammate.domain.projectassignment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.domain.project.repository.ProjectRepository;
import sk.teammate.domain.projectassignment.dto.ProjectAssignmentRequest;
import sk.teammate.domain.projectassignment.dto.ProjectAssignmentResponse;
import sk.teammate.domain.projectassignment.repository.ProjectAssignmentRepository;
import sk.teammate.domain.role.repository.RoleRepository;
import sk.teammate.entity.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectAssignmentService {

    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final RoleRepository RoleRepository;

    @Transactional(readOnly = true)
    public List<ProjectAssignmentResponse> getAssignments(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

        return projectAssignmentRepository.findByProject(project).stream()
                .map(ProjectAssignmentResponse::from)
                .toList();
    }

    @Transactional
    public void assign(Long projectId, String assignedById, ProjectAssignmentRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        Member assigner = memberRepository.findById(assignedById)
                .orElseThrow(() -> new EntityNotFoundException("배정자를 찾을 수 없습니다."));

        Role role = RoleRepository.findById(request.roleId())
                .orElseThrow(() -> new EntityNotFoundException("프로젝트 역할을 찾을 수 없습니다."));

        ProjectAssignment assignment = ProjectAssignment.builder()
                .project(project)
                .member(member)
                .role(role)
                .assignedBy(assigner)
                .assignedByAi(request.assignedByAi())
                .assignedAt(LocalDateTime.now())
                .build();

        member.updateStatus(Member.MemberStatus.ASSIGNED);
        projectAssignmentRepository.save(assignment);
    }

    @Transactional
    public void unassign(Long assignmentId) {
        ProjectAssignment assignment = projectAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("배정 정보를 찾을 수 없습니다."));

        assignment.getMember().updateStatus(Member.MemberStatus.AVAILABLE);
        assignment.markDeleted();
    }
}
