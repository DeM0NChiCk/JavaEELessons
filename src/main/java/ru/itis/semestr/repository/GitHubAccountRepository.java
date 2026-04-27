package ru.itis.semestr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.semestr.entity.GitHubAccountEntity;

import java.util.Optional;

public interface GitHubAccountRepository extends JpaRepository<GitHubAccountEntity, Long> {
    Optional<GitHubAccountEntity> findByGithubUsername(String githubUsername);
}
