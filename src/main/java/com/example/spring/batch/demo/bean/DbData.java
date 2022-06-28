package com.example.spring.batch.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
