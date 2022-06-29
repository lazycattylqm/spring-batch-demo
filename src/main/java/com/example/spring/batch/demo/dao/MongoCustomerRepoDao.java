package com.example.spring.batch.demo.dao;

import com.example.spring.batch.demo.bean.mongo.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCustomerRepoDao extends MongoRepository<CustomerMongo, String> {
}
