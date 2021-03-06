package com.example.spring.batch.demo.bean;

import lombok.Data;

@Data
public class CustomerId {
    private String customerId;
    private String customerCountryCode;
    private String customerInstitutionCode;

    public static CustomerId fromDbDataId(String customerId) {
        assert customerId != null;
        CustomerId id = new CustomerId();
        id.setCustomerCountryCode(customerId.substring(0, 2));
        id.setCustomerInstitutionCode(customerId.substring(2, 6));
        id.setCustomerId(customerId.substring(6));
        return id;
    }
}
