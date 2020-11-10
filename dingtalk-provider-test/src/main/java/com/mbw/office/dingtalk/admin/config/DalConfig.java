package com.mbw.office.dingtalk.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Maps;
import com.smthit.framework.dal.bettlsql.SqlKitHolder;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
//		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(new FilterAdapter(){
//			@Override
//			public DruidPooledConnection dataSource_getConnection(FilterChain chain, DruidDataSource dataSource, long maxWaitMillis) throws SQLException {
//				int activeCount = dataSource.getActiveCount();
//				if (activeCount > (dataSource.getMaxActive() * 0.8)) {
//
//					log.warn("dingDataSource Warning, the current active count of Druid Connection Pool is " + activeCount + "!");
//				}
//
//				return chain.dataSource_connect(dataSource, maxWaitMillis);
//			}
//		});
//		dataSource.setProxyFilters(filters);

		return dataSource;
	}
	
	/**
	 * OA订单明细数据表，只读数据源
	 * @return
	 */
	@Bean("groupDataSource")
	@ConfigurationProperties("group.spring.datasource.druid")
	public DataSource dataVbankSource(){
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
//		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(new FilterAdapter(){
//			@Override
//			public DruidPooledConnection dataSource_getConnection(FilterChain chain, DruidDataSource dataSource, long maxWaitMillis) throws SQLException {
//				int activeCount = dataSource.getActiveCount();
//				if (activeCount > (dataSource.getMaxActive() * 0.8)) {
//					log.warn("groupDataSource Warning, the current active count of Druid Connection Pool is " + activeCount + "!");
//				}
//
//				return chain.dataSource_connect(dataSource, maxWaitMillis);
//			}
//		});
//		dataSource.setProxyFilters(filters);

		return dataSource;
	}

	/**
	 * OA订单明细数据表，只读数据源
	 * @return
	 */
	@Bean("vbankDataSource")
	@ConfigurationProperties("vbank.spring.datasource.druid")
	public DataSource dataGroupSource(){
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
//		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(new FilterAdapter(){
//			@Override
//			public DruidPooledConnection dataSource_getConnection(FilterChain chain, DruidDataSource dataSource, long maxWaitMillis) throws SQLException {
//				int activeCount = dataSource.getActiveCount();
//				if (activeCount > (dataSource.getMaxActive() * 0.8)) {
//					log.warn("vbankDataSource Warning, the current active count of Druid Connection Pool is " + activeCount + "!");
//				}
//
//				return chain.dataSource_connect(dataSource, maxWaitMillis);
//			}
//		});
//		dataSource.setProxyFilters(filters);

		return dataSource;
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
		holder.addDS("groupDataSource");

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