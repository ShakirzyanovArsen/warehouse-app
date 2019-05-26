package xyz.shakirzyanov.warehouseapp.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import xyz.shakirzyanov.warehouseapp.model.User;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUuid(rs.getString("uuid"));
        user.setLogin(rs.getString("login"));
        user.setPwd(rs.getString("pwd"));
        Array publicKeys = rs.getArray("publicKeys");
        user.setPublicKeys((String[]) publicKeys.getArray());
        return user;
    }
}
