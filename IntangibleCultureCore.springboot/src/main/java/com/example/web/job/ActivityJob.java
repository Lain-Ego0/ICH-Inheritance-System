package com.example.web.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.web.service.ActivityService;


@Component
public class ActivityJob {
   

    @Autowired
    private ActivityService ActivityService;

    // 每隔5s执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void AutomaticallyActivityChangeStatus() {
        ActivityService.AutomaticallyActivityChangeStatus();
    }

}
