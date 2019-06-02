package xyz.shakirzyanov.warehouseapp.model;

import xyz.shakirzyanov.warehouseapp.model.enums.DocumentType;

import java.util.Date;
import java.util.List;

public class Document {
    private String uuid;
    private String userUuid;
    private String warehouseUuid;
    private String clientUuid;
    private List<String> goods;
    private List<Double> counts;
    private DocumentType documentType;
    private String fileName;
    private String fileHash;
    private String sigFileName;
    private Integer ended;
    private Date createdAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getWarehouseUuid() {
        return warehouseUuid;
    }

    public void setWarehouseUuid(String warehouseUuid) {
        this.warehouseUuid = warehouseUuid;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public List<String> getGoods() {
        return goods;
    }

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    public List<Double> getCounts() {
        return counts;
    }

    public void setCounts(List<Double> counts) {
        this.counts = counts;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getSigFileName() {
        return sigFileName;
    }

    public void setSigFileName(String sigFileName) {
        this.sigFileName = sigFileName;
    }

    public Integer getEnded() {
        return ended;
    }

    public void setEnded(Integer ended) {
        this.ended = ended;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
