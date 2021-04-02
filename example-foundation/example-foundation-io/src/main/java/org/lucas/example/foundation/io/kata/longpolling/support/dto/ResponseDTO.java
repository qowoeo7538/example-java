package org.lucas.example.foundation.io.kata.longpolling.support.dto;

public class ResponseDTO {

    private String data;

    private Long version;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
