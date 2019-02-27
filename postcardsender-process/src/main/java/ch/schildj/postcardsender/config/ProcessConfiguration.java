package ch.schildj.postcardsender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

/**
 * ProcessConfiguration
 * Configuration file
 */

@Configuration
@EnableScheduling
@EnableAsync
@Import({DomainConfiguration.class})
@ComponentScan({"ch.schildj.postcardsender.process", "ch.schildj.postcardsender.apicall.process"})
public class ProcessConfiguration {
    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }


    @Bean(name = "threadPoolTaskExecutor")
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
