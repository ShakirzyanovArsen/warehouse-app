package xyz.shakirzyanov.warehouseapp.dto;

public class FileSig {
    private String uuid;
    private String sigBase64;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSigBase64() {
        return sigBase64;
    }

    public void setSigBase64(String sigBase64) {
        this.sigBase64 = sigBase64;
    }
}
