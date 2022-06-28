package com.example.spring.batch.demo.bean;

import lombok.Data;

@Data
public class Id {
    private String customerId;
    private String customerCountryCode;
    private String customerInstitutionCode;
    private String userId;

    public static Id fromDbDataId(String customerId, String userId) {
        assert customerId != null;
        assert userId != null;
        Id id = new Id();
        id.setCustomerCountryCode(customerId.substring(0, 2));
        id.setCustomerInstitutionCode(customerId.substring(2, 6));
        id.setCustomerId(customerId.substring(6));
        id.setUserId(userId);
        return id;
    }

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
