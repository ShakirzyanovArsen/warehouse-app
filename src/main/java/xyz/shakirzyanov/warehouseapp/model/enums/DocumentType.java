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

    public static DocumentType byTitle(String title) {
        switch (title) {
            case "Приход":
                return DocumentType.IN;
            case "Расход":
                return DocumentType.OUT;
            default:
                throw new IllegalArgumentException("Unrecognized DocumentType title");
        }
    }
}
