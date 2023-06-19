package com.example.spring.batch.demo.job;

import com.example.spring.batch.demo.model.Model;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Model, Model> {
    @Override
    public Model process(Model item) throws Exception {
        return item;
    }
}
