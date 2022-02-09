package com.poscoict.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/poscoict/mysite/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	/* Connection Pool DataSource */
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		// 성능 향상 작업 (뭐라고 하더라..)
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize",Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive",Integer.class)); // 아무리 많은 요청이 들어와도 최대 100개만 만들어라
		
		return dataSource;
	}
	
}
