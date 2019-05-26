package xyz.shakirzyanov.warehouseapp.model;

import xyz.shakirzyanov.warehouseapp.model.enums.GoodsUnit;

import java.util.Date;

public class Goods {
    private String uuid;
    private Double count;
    private GoodsUnit unit;
    private String barcode;
    private Date createdAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public GoodsUnit getUnit() {
        return unit;
    }

    public void setUnit(GoodsUnit unit) {
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getCreatedAt() {
        if(createdAt == null) {
            createdAt = new Date();
        }
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
