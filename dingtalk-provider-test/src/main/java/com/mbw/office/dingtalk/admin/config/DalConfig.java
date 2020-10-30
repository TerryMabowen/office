package com.mbw.office.dingtalk.admin.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Maps;
import com.smthit.framework.dal.bettlsql.SqlKitHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 
 * @author zlx
 *
 */
@Configuration
public class DalConfig {
	/**
	 * 虚拟银行主数据员
	 * @return
	 */
	@Bean("dataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.druid")
	public DataSource dataSource() {
		return DruidDataSourceBuilder.create().build();
	}
	
	/**
	 * OA订单明细数据表，只读数据源
	 * @return
	 */
	@Bean("vbankDataSource")
	@ConfigurationProperties("vbank.spring.datasource.druid")
	public DataSource dataOaSource(){
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConditionalOnBean(name = {"dataSource"})
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}	
	
	@Bean
	public SqlKitHolder sqlKitHolder() {
		SqlKitHolder holder = new SqlKitHolder();
		holder.setSupportMultiDS(true);
		holder.setMasterDS("dataSource");
		holder.addDS("vbankDataSource");
		
		return holder;
	}

	/**
	 * druid 监控配置
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean();
		Map<String, String> initParams = Maps.newHashMap();
		initParams.put("loginUsername", "root");
		initParams.put("loginPassword", "123456");
		//默认就是允许所有访问
		initParams.put("allow", "");
		// 禁用HTML页面上的“Reset All”功能
		initParams.put("resetEnable", "false");
		bean.setInitParameters(initParams);
		bean.setServlet(new StatViewServlet());
		//url 匹配
		bean.addUrlMappings("/druid/*");
		return bean;
	}
}