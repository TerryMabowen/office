package com.mbw.office.demo.web.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-01 11:00
 */
@Configuration
public class DataSourceConfig {
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

    /**
     * druid 监控配置
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "Bell.Ai504");
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
