package xyz.shakirzyanov.warehouseapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.Goods;

import java.util.List;


@Repository
public class GoodsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Goods goods) {
        jdbcTemplate.update("INSERT INTO goods(uuid, count, unit, barcode, created_at) VALUES (?, ?, ?, ?, ?)",
                goods.getUuid(), goods.getCount(), goods.getUnit().getValue(), goods.getBarcode(), goods.getCreatedAt());
    }

    public Page<Goods> findGoodsByPage(Pageable page) {
        var count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM goods FINAL", Long.class);
        var sql = "SELECT * FROM goods FINAL ORDER BY goods.created_at DESC LIMIT ? OFFSET ?";
        var goods = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Goods.class),
                page.getPageSize(), page.getOffset());
        return new PageImpl<>(goods, page, count);
    }
}
