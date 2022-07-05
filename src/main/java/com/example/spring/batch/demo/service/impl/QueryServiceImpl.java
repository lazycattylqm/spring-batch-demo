package com.example.spring.batch.demo.service.impl;

import com.example.spring.batch.demo.bean.CustomerId;
import com.example.spring.batch.demo.bean.mongo.CustomerMongo;
import com.example.spring.batch.demo.bean.mysql.CustomerRepo;
import com.example.spring.batch.demo.dao.CustomerRepoDao;
import com.example.spring.batch.demo.dao.MongoCustomerRepoDao;
import com.example.spring.batch.demo.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            System.out.println(customerRepo);
            list.add(CustomerMongo.fromDbData(customerRepo));
        }
        mongoCustomerRepoDao.saveAll(list);

    }

    @Override
    public List<CustomerMongo> queryMongo() {
        return mongoCustomerRepoDao.findAll();
    }

    @Override
    @Transactional("mongoTransactionManager")
    public void insertTwoToMongo() {
        CustomerId hw = CustomerId.builder()
                .customerId("1243")
                .customerInstitutionCode("hw")
                .customerCountryCode("cn`")
                .build();
        CustomerMongo build = CustomerMongo.builder()
                .customerId(hw)
                .entitle("hello")
                .description("world")
                .build();
        CustomerMongo build1 = CustomerMongo.builder()
                .customerId(hw)
                .entitle("hello2")
                .description("world2")
                .build();

        mongoCustomerRepoDao.save(build);

        mongoCustomerRepoDao.save(build1);
        throw new RuntimeException("error");

    }
}
