package com.example.spring.batch.demo.data.source.config.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before job");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String s = jobExecution.getStatus()
                .toString();
        System.out.println("After job " + s);

    }
}
