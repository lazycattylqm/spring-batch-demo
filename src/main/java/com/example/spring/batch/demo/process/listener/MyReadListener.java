package com.example.spring.batch.demo.process.listener;

import com.example.spring.batch.demo.bean.DbData;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadListener implements ItemReadListener<DbData> {
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(DbData dbData) {
        System.out.println(dbData);
    }

    @Override
    public void onReadError(Exception e) {

    }
}
