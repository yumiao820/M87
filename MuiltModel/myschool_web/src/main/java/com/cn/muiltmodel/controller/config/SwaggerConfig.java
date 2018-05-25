package com.cn.muiltmodel.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置文件
 *
 */
@EnableWebMvc //开启spring注解
@Configuration //标注为配置文件
@EnableSwagger2 //启用swagger
@ComponentScan("com.cn.muiltmodel.controller.config")//扫描swaggger所在的包
public class SwaggerConfig  {

    @Bean
    public Docket CreateRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().description("126").title("126高新公司").version("v1.0").build();
    }
}
