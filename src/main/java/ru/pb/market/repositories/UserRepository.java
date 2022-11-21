package ru.pb.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pb.market.data.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByUsername(String name);
}
