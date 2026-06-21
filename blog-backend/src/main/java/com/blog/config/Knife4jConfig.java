package com.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j API文档配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("个人博客系统API")
                .description("简洁高效的程序员个人博客系统接口文档")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Blog Admin")
                    .email("admin@blog.com")));
    }
}
