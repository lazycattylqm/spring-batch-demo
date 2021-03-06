package com.example.spring.batch.demo.service.impl;

import com.example.spring.batch.demo.bean.CustomerMongo;
import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.repo.CustomerRepoDao;
import com.example.spring.batch.demo.repo.MongoCustomerRepoDao;
import com.example.spring.batch.demo.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryServiceImpl implements QueryService {

    private CustomerRepoDao customerRepoDao;

    private MongoCustomerRepoDao mongoCustomerRepoDao;

    @Autowired
    public void setMongoCustomerRepoDao(MongoCustomerRepoDao mongoCustomerRepoDao) {
        this.mongoCustomerRepoDao = mongoCustomerRepoDao;
    }

    @Autowired
    public void setCustomerRepoDao(CustomerRepoDao customerRepoDao) {
        this.customerRepoDao = customerRepoDao;
    }

    @Override
    public void query() {
        Iterable<CustomerRepo> all = customerRepoDao.findAll();
        List<CustomerMongo> list = new ArrayList<>();
        for (CustomerRepo customerRepo : all) {
            CustomerMongo customerMongo = CustomerMongo.fromDbData(customerRepo);
            list.add(customerMongo);
        }
        mongoCustomerRepoDao.saveAll(list);

    }
}
