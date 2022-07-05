package com.example.spring.batch.demo.bean.mongo;

import com.example.spring.batch.demo.bean.CustomerId;
import com.example.spring.batch.demo.bean.mysql.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer_collection")
@Builder
public class CustomerMongo {

    @MongoId
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
