package cl.tomouno.userservice.repository;

import cl.tomouno.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    User findByUsername(String username);
}
