package com.yidiansishiyi.searchCenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("青鸟搜索接口管理")
                        .description("qingsou")
                        .version("1.0")

                .description("> 允许用户在同一个页面集中搜索出 **不同来源**、**不同类型** 的内容，提升用户的检索效率和搜索体验当企业有多个项目的数据需要被搜索时，无需针对每个项目单独开发搜索功能，可以直接将数据接入搜索中台，提升开发效率。\n" +
                        ">\n" +
                        "> 本项目为聚合搜索平台,目前支持数据源为帖子,存储源为服务器初始数据从网络上抓取,图片从必应接口获取,用户存储源为服务器,且具有良好的扩展性,为初初始版本,搜索引擎 es 的引入正在优化中,前端正在开发中。\n" +
                        "\n" +
                        "**Author:** sanqi\n" +
                        "\n" +
                        "**Email:** [nuomibaicha@163.com](nuomibaicha@163.com)")
                .termsOfServiceUrl("124.222.184.3")
                        .build())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.yidiansishiyi.searchCenter.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}