package com.mbw.office.learn.admin.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 
 * @author dinghq
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		ParameterBuilder authPar = new ParameterBuilder();
        List<Parameter> pars = Lists.newArrayList();
        authPar.name("Authorization").description("授权token")
    	.modelRef(new ModelRef("string")).parameterType("header")
		//header中的Authorization参数非必填，传空也可以
    	.required(false).build();
    	pars.add(authPar.build()); 
    	
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mbw.office.learn"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("office-learn平台接口")
				.description("office-learn平台接口")
				.termsOfServiceUrl("http://localhost:7289")
				.version("1.0")
				.build();
	}

}
