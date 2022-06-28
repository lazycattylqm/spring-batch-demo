package com.example.spring.batch.demo.repo;

import com.example.spring.batch.demo.bean.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCustomerRepoDao  extends MongoRepository<CustomerMongo, String> {
}
