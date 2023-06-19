package com.example.spring.batch.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
public class BatchE2eTest {

    @Test
    void e2etest(@Autowired JobLauncherTestUtils jobLauncherTestUtils, @Autowired Job job) throws Exception {
        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
}
