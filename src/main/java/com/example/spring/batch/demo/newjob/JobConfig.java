package com.example.spring.batch.demo.newjob;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
//@Configuration
public class JobConfig {
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public void setStepBuilderFactory(
            StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step stepPrint() {
        return stepBuilderFactory.get("stepPrint")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("stepPrint Hello Quartz");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
