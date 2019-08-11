package xyz.shakirzyanov.warehouseapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.repository.mapper.UserMapper;

import java.util.Date;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper mapper;

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO user (uuid, login, pwd, publicKeys, created_at) VALUES (?, ?, ?, ?, ?)",
                user.getUuid(), user.getLogin(), user.getPwd(), user.getPublicKeys(), new Date());
    }

    public User findByLogin(String login) {
        var users = jdbcTemplate.query("SELECT * FROM user FINAL WHERE login = ?", mapper, login);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }
}
