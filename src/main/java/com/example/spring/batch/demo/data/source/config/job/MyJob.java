package com.example.spring.batch.demo.data.source.config.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MyJob {

    private JobBuilderFactory jobBuilderFactory;

    public MyJob(@Autowired JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public JobExecutionListener liqimingListener() {
        return new JobListener();
    }

    @Bean
    public Job liqimingJob(Step myStep, JobExecutionListener liqimingListener) {
        return jobBuilderFactory.get("liqimingJob")
                .incrementer(new RunIdIncrementer())
                .listener(liqimingListener)
                .flow(myStep)
                .end()
                .build();
    }
}
