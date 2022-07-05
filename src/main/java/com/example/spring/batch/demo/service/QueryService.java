package com.example.spring.batch.demo.service;

import com.example.spring.batch.demo.bean.mongo.CustomerMongo;

import java.util.List;

public interface QueryService {
    void query();

    List<CustomerMongo> queryMongo();


    void insertTwoToMongo();
}
