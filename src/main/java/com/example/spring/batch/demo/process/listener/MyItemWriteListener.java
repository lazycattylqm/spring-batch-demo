package com.example.spring.batch.demo.process.listener;

import com.example.spring.batch.demo.bean.MongoData;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyItemWriteListener implements ItemWriteListener<MongoData> {
    @Override
    public void beforeWrite(List<? extends MongoData> list) {
    }

    @Override
    public void afterWrite(List<? extends MongoData> list) {
        list.forEach(System.out::println);
    }

    @Override
    public void onWriteError(Exception e, List<? extends MongoData> list) {

    }
}
