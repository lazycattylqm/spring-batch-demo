package com.example.spring.batch.demo.paging.step;

import com.example.spring.batch.demo.bean.CustomerRepo;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class BeansWriter implements ItemWriter<CustomerRepo> {

    public void write(Iterable<? extends CustomerRepo> items) throws Exception {
        items.forEach(System.out::println);
    }

    @Override
    public void write(List<? extends CustomerRepo> list) throws Exception {
        list.forEach(System.out::println);
    }
}
