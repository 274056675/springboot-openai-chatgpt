package org.springblade.mng.config.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
public class SyncPoolConfiguration{

	@Bean(name = "asyncPoolTaskExecutor")
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		//核心线程数
		taskExecutor.setCorePoolSize(50);
		//线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
		taskExecutor.setMaxPoolSize(200);
		//缓存队列
		taskExecutor.setQueueCapacity(100);
		//许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
		taskExecutor.setKeepAliveSeconds(2000);
		//异步方法内部线程名称
		taskExecutor.setThreadNamePrefix("mjkj-async-");
		/**
		 * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
		 * 通常有以下四种策略：
		 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
		 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
		 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
		 * ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用 execute() 方法，直到成功
		 */
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.initialize();
		return taskExecutor;
	}

}
