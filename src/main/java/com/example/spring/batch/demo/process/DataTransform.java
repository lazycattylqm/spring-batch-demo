package com.example.spring.batch.demo.process;

import com.example.spring.batch.demo.bean.DbData;
import com.example.spring.batch.demo.bean.MongoData;
import org.springframework.batch.item.ItemProcessor;

public class DataTransform implements ItemProcessor<DbData, MongoData> {
    @Override
    public MongoData process(DbData dbData) throws Exception {
        return MongoData.fromDbData(dbData);
    }
}
