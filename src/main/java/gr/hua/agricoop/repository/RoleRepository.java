package gr.hua.agricoop.repository;

import gr.hua.agricoop.entity.role;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Hidden
public interface RoleRepository extends JpaRepository<role, Integer> {

    Optional<role> findByName(String roleName);
}