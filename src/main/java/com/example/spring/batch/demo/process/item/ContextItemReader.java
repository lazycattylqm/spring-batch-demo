package com.example.spring.batch.demo.process.item;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContextItemReader implements ItemReader<String> {
    private StepExecution stepExecution;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException {

        stepExecution.getExecutionContext()
                .put("key", "lqm");
        return "hello quartz";
    }

    @BeforeStep
    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
