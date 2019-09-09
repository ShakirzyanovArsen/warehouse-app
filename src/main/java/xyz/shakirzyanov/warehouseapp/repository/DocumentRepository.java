package xyz.shakirzyanov.warehouseapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.shakirzyanov.warehouseapp.model.Document;
import xyz.shakirzyanov.warehouseapp.repository.mapper.DocumentMapper;

@Repository
public class DocumentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DocumentMapper documentMapper;

    public void save(Document doc) {
        jdbcTemplate.update("INSERT INTO document(uuid, userUuid, warehouseUuid, clientUuid, goods, counts, type, fileName, fileHash, sigFileName, ended, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                doc.getUuid(), doc.getUserUuid(), doc.getWarehouseUuid(), doc.getClientUuid(),
                doc.getGoods(), doc.getCounts(), doc.getDocumentType().getTitle(), doc.getFileName(),
                doc.getFileHash(), doc.getSigFileName(), doc.getEnded(), doc.getCreatedAt());
    }

    public Document findByUUID(String uuid) {
        var documents = jdbcTemplate.query("SELECT * FROM document FINAL WHERE uuid = ?", documentMapper, uuid);
        if(documents.size() == 0) {
            return null;
        }
        return documents.get(0);
    }
}
