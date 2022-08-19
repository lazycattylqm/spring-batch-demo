package com.example.spring.batch.demo.paging.step;

import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.bean.DbData;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class BeansWriter implements ItemWriter<DbData> {

    public void write(Iterable<? extends DbData> items) throws Exception {
        items.forEach(System.out::println);
    }

    @Override
    public void write(List<? extends DbData> list) throws Exception {
        list.forEach(System.out::println);
    }
}
