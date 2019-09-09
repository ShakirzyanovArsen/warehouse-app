package xyz.shakirzyanov.warehouseapp.repository.mapper;

import com.google.common.primitives.Doubles;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import xyz.shakirzyanov.warehouseapp.model.Document;
import xyz.shakirzyanov.warehouseapp.model.enums.DocumentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DocumentMapper implements RowMapper<Document> {
    @Override
    public Document mapRow(ResultSet resultSet, int i) throws SQLException {
        Document document = new Document();
        document.setUuid(resultSet.getString("uuid"));
        document.setUserUuid(resultSet.getString("userUuid"));
        document.setWarehouseUuid(resultSet.getString("warehouseUuid"));
        document.setClientUuid(resultSet.getString("clientUuid"));
        document.setGoods(List.of((String[])resultSet.getArray("goods").getArray()));
        double[] counts = (double[]) resultSet.getArray("counts").getArray();
        document.setCounts(Doubles.asList(counts));
        document.setDocumentType(DocumentType.byTitle(resultSet.getString("type")));
        document.setFileName(resultSet.getString("fileName"));
        document.setFileHash(resultSet.getString("fileHash"));
        document.setSigFileName(resultSet.getString("sigFileName"));
        document.setEnded(resultSet.getInt("ended"));
        document.setCreatedAt(resultSet.getDate("created_at"));
        return document;
    }
}
