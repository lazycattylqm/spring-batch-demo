package com.example.spring.batch.demo.bean;

import lombok.Data;

@Data
public class Id {
    private String customerId;
    private String customerCountryCode;
    private String customerInstitutionCode;
    private String userId;

    @Override
    public String toString() {
        return "Id{" +
                "customerId='" + customerId + '\'' +
                ", customerCountryCode='" + customerCountryCode + '\'' +
                ", customerInstitutionCode='" + customerInstitutionCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
