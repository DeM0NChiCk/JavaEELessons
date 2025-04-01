package ru.itis.lessonservlet.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.lessonservlet.mapper.UserMapper;
import ru.itis.lessonservlet.model.UserEntity;
import ru.itis.lessonservlet.repository.UserRepository;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";

    private static final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";

    private static final String SQL_SELECT_BY_USERNAME = "select * from users where username = ?";

    private static final String SQL_INSERT = "insert into users(email, hash_password, username, role) values (?, ?, ?, ?)";

    private final UserMapper userRowMapper;


    @Override
    public Optional<UserEntity> findUserById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, userRowMapper, username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> saveNewUser(UserEntity user) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_INSERT, new String[] {"id"});
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getHashPassword());
                ps.setString(3, user.getUsername());
                ps.setString(4, user.getRole());
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findUserById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
