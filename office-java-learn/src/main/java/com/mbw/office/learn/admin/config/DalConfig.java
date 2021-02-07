/**
 * 
 */
package com.mbw.office.learn.admin.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.mbw.office.learn.biz.lang.helper.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @descrition
 * @author dinghq
 */
@Configuration
public class DalConfig {

	/**
	 * 主数据源，支持读写和事务
	 */
	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.druid")
	public DataSource dataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 主数据源事务管理器
	 */
	@Bean
	@ConditionalOnBean(name = { "dataSource" })
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public ApplicationContextHelper springContextHelper() {
		return new ApplicationContextHelper();
	}
}
