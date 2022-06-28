package com.example.spring.batch.demo.process.item;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ContextItemReader implements ItemReader<String> {
    private StepExecution stepExecution;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException {

        int o = ((int) Optional.ofNullable(stepExecution.getExecutionContext()
                        .get("key"))
                .orElse(0));

        if (o > 5) {
            return null;
        }
        stepExecution.getExecutionContext()
                .putInt("key", o + 1);
        log.info("stepExecution.getExecutionContext() = {}", stepExecution.getExecutionContext());
        return "hello quartz";
    }

    @BeforeStep
    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @AfterRead
    @AfterStep
    public ExitStatus afterStep() {
        stepExecution.getJobExecution()
                .setStatus(BatchStatus.COMPLETED);
        return ExitStatus.COMPLETED;
    }
}
