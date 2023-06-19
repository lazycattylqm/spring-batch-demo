package com.example.spring.batch.demo.job;

import com.example.spring.batch.demo.model.Model;
import com.example.spring.batch.demo.serivce.OpService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Reader implements ItemReader<Model> {

    private final OpService opService;

    public Reader(OpService opService) {
        this.opService = opService;
    }

    private List<Model> results = new CopyOnWriteArrayList<>();

    @Override
    public Model read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (results.isEmpty()) {
            results = opService.get();
        }
        if (results.isEmpty()) {
            return null;
        }
        return results.remove(0);
    }
}
