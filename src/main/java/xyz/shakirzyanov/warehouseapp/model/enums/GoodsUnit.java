package xyz.shakirzyanov.warehouseapp.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GoodsUnit {
    @JsonProperty("кг.")
    KG("кг.", 1),
    @JsonProperty("л.")
    LITER("л.", 2),
    @JsonProperty("шт.")
    SHT("шт.", 3);
    private String title;
    private Integer value;

    GoodsUnit(String title, Integer value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public Integer getValue() {
        return value;
    }

    public static GoodsUnit byTitle(String title) {
        switch (title) {
            case "кг.":
                return KG;
            case "л.":
                return LITER;
            case "шт.":
                return SHT;
            default:
                throw new IllegalArgumentException("Unrecognized GoodsUnit title");
        }
    }
}
