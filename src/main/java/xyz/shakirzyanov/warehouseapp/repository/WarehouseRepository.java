package xyz.shakirzyanov.warehouseapp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;

@Repository
public class WarehouseRepository {

    private Logger logger = LoggerFactory.getLogger(WarehouseRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Warehouse warehouse) {
        jdbcTemplate.update("INSERT INTO warehouse (uuid, name, address, created_at) VALUES (?, ?, ?, ?)",
                warehouse.getUuid(), warehouse.getName(), warehouse.getAddress(), warehouse.getCreatedAt());
    }

    public Warehouse findByUuid(String uuid) {
            var warehouses = jdbcTemplate.query("SELECT * FROM warehouse FINAL WHERE uuid = ?",
                    new BeanPropertyRowMapper<>(Warehouse.class), uuid);
            if (warehouses.size() == 0) {
                return null;
            }
            return warehouses.get(0);
    }

    public Page<Warehouse> findWarehousesByPage(Pageable page) {
        var count = jdbcTemplate.queryForObject("SELECT count(*) FROM warehouse FINAL", Long.class);
        var sql = "SELECT * FROM warehouse FINAL ORDER BY warehouse.created_at DESC LIMIT ? OFFSET ?";
        var warehouses = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Warehouse.class), page.getPageSize(), page.getOffset());
        return new PageImpl<>(warehouses, page, count);
    }

}
