package com.example.spring.batch.demo.job;

import com.example.spring.batch.demo.model.Model;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<Model> {
    @Override
    public void write(List<? extends Model> items) throws Exception {
        System.out.println("Writer.write");
        items.forEach(System.out::println);
    }
}
