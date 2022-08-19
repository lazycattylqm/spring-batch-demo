package com.example.spring.batch.demo.paging.step;

import com.example.spring.batch.demo.bean.CustomerRepo;
import org.springframework.batch.item.ItemProcessor;

public class BeanProcessor implements ItemProcessor<CustomerRepo, CustomerRepo> {
    @Override
    public CustomerRepo process(CustomerRepo customerRepo) throws Exception {
        return customerRepo;
    }
}
