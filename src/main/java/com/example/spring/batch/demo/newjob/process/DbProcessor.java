package com.example.spring.batch.demo.newjob.process;

import com.example.spring.batch.demo.bean.CustomerMongo;
import com.example.spring.batch.demo.bean.CustomerRepo;
import org.springframework.batch.item.ItemProcessor;

public class DbProcessor implements ItemProcessor<CustomerRepo, CustomerMongo> {

    @Override
    public CustomerMongo process(CustomerRepo customerRepo) throws Exception {
        return CustomerMongo.fromDbData(customerRepo);
    }
}
