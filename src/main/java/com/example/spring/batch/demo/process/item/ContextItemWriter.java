package com.example.spring.batch.demo.process.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@StepScope
@Component
public class ContextItemWriter implements ItemWriter<String> {

    private StepExecution stepExecution;

    private String key;
    @Override
    public void write(List<? extends String> list) throws Exception {
        Object key1 = stepExecution.getExecutionContext()
                .get("key");
        log.info("key1: {}", key1);
        list.forEach(item -> log.info("ContextItemWriter write {}", item));
    }

    @BeforeStep
    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
