package com.example.spring.batch.demo.process.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getJobExecution()
                .getExecutionContext()
                .putString("jobName", stepExecution.getJobExecution()
                        .getJobInstance()
                        .getJobName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getJobExecution()
                .getExecutionContext()
                .get("jobName"));
        return ExitStatus.COMPLETED;
    }
}
