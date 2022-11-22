package ru.pb.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pb.market.data.Role;
import ru.pb.market.data.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findRoleByName(String name);

}
