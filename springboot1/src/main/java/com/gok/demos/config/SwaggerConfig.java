package com.gok.demos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {



    @Bean
    public Docket docket(){
//        DocumentationType.OAS_30 设置文档类型为openapi 3.0
       return new Docket(DocumentationType.OAS_30)
//               api的基本信息
               .apiInfo(apiInfo())
               .select()//选择哪些接口暴露给swagger
//               扫描所有的controller 包下的接口
               .apis(RequestHandlerSelectors.any())
               .paths(PathSelectors.any())
               .build();
    }
//  文档的基本信息

    public ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title("元器件管理系统 API")
                .description("基于DDD架构的智能元器件管理系统接口文档\n\n" +
                        "系统功能：\n" +
                        "1. 元器件类型管理（管理员）\n" +
                        "2. 供应商管理（管理员）\n" +
                        "3. 入库管理（普通用户）\n" +
                        "4. 取用管理（普通用户）\n" +
                        "5. 库存统计查询\n" +
                        "6. 取用历史查询")
                .version("v1.0.0")
                .contact(new Contact("元器件管理系统开发团队","http://localhost:8080","contact@example.com"))
                .build();
    }


}
