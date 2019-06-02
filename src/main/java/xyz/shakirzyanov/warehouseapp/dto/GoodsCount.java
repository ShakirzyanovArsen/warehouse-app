package xyz.shakirzyanov.warehouseapp.dto;

public class GoodsCount {
    private String goodsUuid;
    private Double count;

    public String getGoodsUuid() {
        return goodsUuid;
    }

    public void setGoodsUuid(String goodsUuid) {
        this.goodsUuid = goodsUuid;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
