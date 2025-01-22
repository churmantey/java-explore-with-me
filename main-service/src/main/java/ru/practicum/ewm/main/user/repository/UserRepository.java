package ru.practicum.ewm.main.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}