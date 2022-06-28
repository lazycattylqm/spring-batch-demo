package com.example.spring.batch.demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobBean2 {
    public void doIt() {
        log.info("doIt");
    }
}
