package com.example.spring.batch.demo.newjob;

import com.example.spring.batch.demo.process.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class JobCom {
    private JobBuilderFactory jobBuilderFactory;

    private Step stepPrint;

    @Autowired
    public void setStepPrint(Step stepPrint) {
        this.stepPrint = stepPrint;
    }

    @Autowired
    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }


    public Job lqmJob() {
        return jobBuilderFactory.get("lqmJob")
                .incrementer(new RunIdIncrementer())
                .start(stepPrint)
                .build();
    }

}
