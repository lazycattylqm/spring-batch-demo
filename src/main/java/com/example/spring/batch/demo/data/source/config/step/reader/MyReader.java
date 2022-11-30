package com.example.spring.batch.demo.data.source.config.step.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class MyReader implements ItemReader<Integer> {

    private int multi = 1;

    private List<Integer> result = new LinkedList<>();

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (multi <= 4) {
            if (result.isEmpty()) {
                loadPage();
                multi++;
            }
            if (result.isEmpty()) {
                return null;
            }
            return result.remove(0);
        }

        return null;
    }


    private void loadPage() {
        result.clear();
        List<Integer> integers = Stream.of(1, 2, 3, 4, 5)
                .map(val -> val * multi)
                .toList();
        result.addAll(integers);
    }
}
