package com.eghm.web.configuration.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * demo
 * @author 二哥很猛
 */
@Slf4j
public class AppSpringApplicationRunListener implements SpringApplicationRunListener {

	public AppSpringApplicationRunListener(SpringApplication application, String[] args) {
	}

	@Override
	public void starting() {
		log.info("自定义starting...");
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {
		log.info("自定义environmentPrepared...");
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		log.info("自定义contextPrepared...");
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		log.info("自定义contextLoaded...");
	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		log.info("自定义started...");
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		log.info("自定义后置处理...");
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		log.info("自定义失败...");
	}
}
