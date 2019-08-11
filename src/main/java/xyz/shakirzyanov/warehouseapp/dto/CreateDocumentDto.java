package xyz.shakirzyanov.warehouseapp.dto;

import xyz.shakirzyanov.warehouseapp.model.enums.DocumentType;

import java.util.List;

public class CreateDocumentDto {

    private DocumentType documentType;
    private String warehouseUuid;
    private String clientUuid;
    private List<GoodsCount> goodsCounts;

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
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

    public List<GoodsCount> getGoodsCounts() {
        return goodsCounts;
    }

    public void setGoodsCounts(List<GoodsCount> goodsCounts) {
        this.goodsCounts = goodsCounts;
    }
}
