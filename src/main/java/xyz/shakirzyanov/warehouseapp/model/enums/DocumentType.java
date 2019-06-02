package xyz.shakirzyanov.warehouseapp.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DocumentType {
    @JsonProperty("Приход")
    IN("Приход", 1),
    @JsonProperty("Расход")
    OUT("Расход", 2);

    private String title;
    private Integer value;

    DocumentType(String title, Integer value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public Integer getValue() {
        return value;
    }
}
