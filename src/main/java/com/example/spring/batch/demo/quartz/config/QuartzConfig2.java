package com.example.spring.batch.demo.quartz.config;

import com.example.spring.batch.demo.quartz.JobBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

//@Configuration
public class QuartzConfig2 {

    private JobBean2 jobBean2;

    @Autowired
    public void setJobBean2(JobBean2 jobBean2) {
        this.jobBean2 = jobBean2;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetObject(jobBean2);
        bean.setTargetMethod("doIt");
        bean.setConcurrent(false);
        return bean;
    }

    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
        bean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        bean.setStartDelay(1000);
        bean.setRepeatInterval(1000);
        return bean;
    }
}
