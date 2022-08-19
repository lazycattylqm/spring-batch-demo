package com.example.spring.batch.demo.controller;

import com.example.spring.batch.demo.newjob.JobCom;
import com.example.spring.batch.demo.service.QueryService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
public class Controller {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobCom job;

    @Autowired
    private Job jpaPagingJob;
    private QueryService queryService;

    @Autowired
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/run")
    public String hello() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        JobParameters parameters = jobParametersBuilder.addDate("date", Date.from(Instant.now())).addLong("maxId", 50L)
                .toJobParameters();

        jobLauncher.run(jpaPagingJob, parameters);
        return "Hello Quartz";
    }

    @GetMapping("/query")
    public String query() {
        queryService.query();
        return "query hello";
    }
}
