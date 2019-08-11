package xyz.shakirzyanov.warehouseapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.Client;

import java.util.List;

@Repository
public class ClientRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Client client) {
        jdbcTemplate.update("INSERT INTO client (uuid, name, phone, email, address, created_at) VALUES " +
                "(?, ?, ?, ?, ?, ?)", client.getUuid(), client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getCreatedAt());
    }

    public Page<Client> findClientsByPage(Pageable page) {
        var count = jdbcTemplate.queryForObject("SELECT count(*) FROM client FINAL", Long.class);
        var sql = "SELECT * FROM client FINAL ORDER BY client.created_at DESC LIMIT ? OFFSET ?";
        var clients = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class), page.getPageSize(), page.getOffset());
        return new PageImpl<>(clients, page, count);
    }

    public Client findByUuid(String uuid) {
        var clients = jdbcTemplate.query("SELECT * FROM client FINAL WHERE uuid = ?",
                new BeanPropertyRowMapper<>(Client.class), uuid);
        if(clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }
}
