package sk.teammate.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    Page<Member> findAllByDeletedAtIsNull(Pageable pageable);
    Page<Member> findAllByStatusAndDeletedAtIsNull(Member.MemberStatus status, Pageable pageable);
}