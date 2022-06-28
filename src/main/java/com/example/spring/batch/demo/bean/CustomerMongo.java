package com.example.spring.batch.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer_collection")
public class CustomerMongo {

    @Id
    private String id;

    private CustomerId customerId;

    private String userId;

    private String entitle;

    private String description;

    public static CustomerMongo fromDbData(CustomerRepo data) {
        CustomerMongo mongoData = new CustomerMongo();
        mongoData.setEntitle(data.getEntitle());
        mongoData.setDescription(data.getDescription());
        mongoData.setUserId(data.getUserId());
        mongoData.setCustomerId(CustomerId.fromDbDataId(data.getCustomerId()));
        return mongoData;
    }
}
