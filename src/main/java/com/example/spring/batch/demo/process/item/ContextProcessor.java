package com.example.spring.batch.demo.process.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContextProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(String item) throws Exception {
        log.info("ContextProcessor process {}", item);
        return item + " " + "Hello Quartz processor";
    }



    @AfterRead
    @AfterStep
    public ExitStatus afterStep() {
        return ExitStatus.COMPLETED;
    }


}
