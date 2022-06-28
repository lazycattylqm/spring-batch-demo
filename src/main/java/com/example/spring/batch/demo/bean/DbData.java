package com.example.spring.batch.demo.bean;

import lombok.Data;

@Data
public class DbData {
    private String customerId;
    private String userId;
    private String entitle;
    private String description;

    @Override
    public String toString() {
        return "DbData{" +
                "customerId='" + customerId + '\'' +
                ", userId='" + userId + '\'' +
                ", entitle='" + entitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
