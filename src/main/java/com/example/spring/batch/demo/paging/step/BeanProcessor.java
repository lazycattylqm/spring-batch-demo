package com.example.spring.batch.demo.paging.step;

import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.bean.DbData;
import org.springframework.batch.item.ItemProcessor;

public class BeanProcessor implements ItemProcessor<DbData, DbData> {
    @Override
    public DbData process(DbData customerRepo) throws Exception {
        return customerRepo;
    }
}
