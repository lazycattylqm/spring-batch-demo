package com.example.spring.batch.demo.process.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
        if (jobExecution.getStatus() == org.springframework.batch.core.BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
        }
    }
}
