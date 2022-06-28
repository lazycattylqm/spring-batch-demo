package com.example.spring.batch.demo.process.listener;

import com.example.spring.batch.demo.bean.DbData;
import org.springframework.batch.core.*;
import org.springframework.batch.core.StepListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.util.Map;

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
