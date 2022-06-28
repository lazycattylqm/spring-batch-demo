package com.example.spring.batch.demo.process.item;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ContextProcessor implements ItemProcessor<String, String> {


    @Override
    public String process(String item) throws Exception {
        return item + " " + "Hello Quartz processor";
    }


}
