package xyz.shakirzyanov.warehouseapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.repository.mapper.GoodsMapper;

import java.util.List;


@Repository
public class GoodsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GoodsMapper mapper;

    public void save(Goods goods) {
        jdbcTemplate.update("INSERT INTO goods(uuid, name, count, unit, barcode, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                goods.getUuid(), goods.getName(), goods.getCount(), goods.getUnit().getTitle(), goods.getBarcode(), goods.getCreatedAt());
    }

    public Page<Goods> findGoodsByPage(Pageable page) {
        var count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM goods FINAL", Long.class);
        var sql = "SELECT * FROM goods FINAL ORDER BY goods.created_at DESC LIMIT ? OFFSET ?";
        var goods = jdbcTemplate.query(sql, mapper,
                page.getPageSize(), page.getOffset());
        return new PageImpl<>(goods, page, count);
    }
}
