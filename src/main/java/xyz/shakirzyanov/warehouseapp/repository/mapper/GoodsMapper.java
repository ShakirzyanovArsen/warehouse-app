package xyz.shakirzyanov.warehouseapp.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.model.enums.GoodsUnit;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GoodsMapper implements RowMapper<Goods> {
    @Override
    public Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
        var goods = new Goods();
        goods.setUuid(rs.getString("uuid"));
        goods.setName(rs.getString("name"));
        goods.setCount(rs.getDouble("count"));
        goods.setUnit(GoodsUnit.byTitle(rs.getString("unit")));
        goods.setBarcode(rs.getString("barcode"));
        goods.setCreatedAt(rs.getDate("created_at"));
        return goods;
    }
}
