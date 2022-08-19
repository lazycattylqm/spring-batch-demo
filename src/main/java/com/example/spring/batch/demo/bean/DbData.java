package com.example.spring.batch.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DbData {

    private Integer id;

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
