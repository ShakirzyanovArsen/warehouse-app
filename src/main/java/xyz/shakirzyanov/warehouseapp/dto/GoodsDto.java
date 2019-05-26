package xyz.shakirzyanov.warehouseapp.dto;

import xyz.shakirzyanov.warehouseapp.model.enums.GoodsUnit;

public class GoodsDto {
    private String name;
    private Double count;
    private GoodsUnit unit;
    private String barcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
