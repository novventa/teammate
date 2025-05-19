package sk.teammate.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

