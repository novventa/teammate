package sk.teammate.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.teammate.entity.AuthToken;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    void deleteByMemberId(String memberId);
}
