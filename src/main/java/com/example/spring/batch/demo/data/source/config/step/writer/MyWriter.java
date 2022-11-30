package com.example.spring.batch.demo.data.source.config.step.writer;

import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;

@StepScope
public class MyWriter implements ItemWriter<Integer> {

    @Override
    public void write(java.util.List<? extends Integer> items) throws Exception {
//        if (items.stream().anyMatch(val->val>7)) {
//            throw new RuntimeException("Error");
//        }
        System.out.println("Write: " + items);
    }

    @BeforeWrite
    public void beforeWrite(java.util.List<? extends Integer> items) {
        System.out.println("Before write: " + items);
    }

    @AfterWrite
    public void afterWrite(java.util.List<? extends Integer> items) {
        System.out.println("After write: " + items);
    }

    @OnWriteError
    public void onWriteError(Exception exception, java.util.List<? extends Integer> items) {
        System.out.println("On write error: " + items);
        exception.printStackTrace();
    }
}
