package org.springyoung.test.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springyoung.test.properties.YoungSwaggerProperties;
import org.springyoung.test.properties.YoungTestProperties;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MyBatis Plus分页插件配置
 */

@Configuration
@AllArgsConstructor
@EnableSwagger2 //表示开启Swagger功能
public class YoungWebConfigure {

    private final YoungTestProperties properties;

    /**
     * 添加Swagger OAuth2认证相关代码:
     * 通过Docket的securitySchemes和securityContexts方法设置了安全策略和安全上下文
     *
     * @return
     */
    @Bean
    public Docket swaggerApi() {
        YoungSwaggerProperties swagger = properties.getSwagger();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //表示将该路径下的所有Controller都添加进去
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                //表示Controller里的所有方法都纳入
                .paths(PathSelectors.any())
                .build()
                //apiInfo用于定义一些API页面信息，比如作者名称，邮箱，网站链接，开源协议等等
                .apiInfo(apiInfo(swagger))
                //用于配置安全策略，比如配置认证模型，scope等内容
                .securitySchemes(Collections.singletonList(securityScheme(swagger)))
                //用于配置安全上下文，只有配置了安全上下文的接口才能使用令牌获取资源
                .securityContexts(Collections.singletonList(securityContext(swagger)));
    }

    private ApiInfo apiInfo(YoungSwaggerProperties swagger) {
        return new ApiInfo(
                swagger.getTitle(),
                swagger.getDescription(),
                swagger.getVersion(),
                null,
                new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()),
                swagger.getLicense(), swagger.getLicenseUrl(), Collections.emptyList());
    }

    /**
     * 分页拦截
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    /**
     * 在securityScheme方法中，我们通过OAuthBuilder对象构建了安全策略
     *
     * @param swagger
     * @return
     */
    private SecurityScheme securityScheme(YoungSwaggerProperties swagger) {
        //主要配置了认证类型为ResourceOwnerPasswordCredentialsGrant（即密码模式），
        //认证地址为http://localhost:8200/auth/oauth/token（即通过网关转发到认证服务器）
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(swagger.getGrantUrl());

        return new OAuthBuilder()
                //安全策略我们将其命名为young_oauth_swagger
                .name(swagger.getName())
                .grantTypes(Collections.singletonList(grantType))
                //scope为test
                .scopes(Arrays.asList(scopes(swagger)))
                .build();
    }

    private SecurityContext securityContext(YoungSwaggerProperties swagger) {
        return SecurityContext.builder()
                //在securityContext方法中，我们通过young_oauth_swagger名称关联了上面定义的安全策略
                .securityReferences(Collections.singletonList(new SecurityReference(swagger.getName(), scopes(swagger))))
                //并且通过forPaths(PathSelectors.any())设置所有API接口都用这个安全上下文
                .forPaths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] scopes(YoungSwaggerProperties swagger) {
        return new AuthorizationScope[]{
                new AuthorizationScope(swagger.getScope(), "")
        };
    }

}