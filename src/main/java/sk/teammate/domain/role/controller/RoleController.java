package sk.teammate.domain.role.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.teammate.domain.role.repository.RoleRepository;
import sk.teammate.entity.Role;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}