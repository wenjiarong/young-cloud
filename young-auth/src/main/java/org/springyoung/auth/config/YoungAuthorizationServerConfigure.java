package org.springyoung.auth.config;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springyoung.auth.properties.YoungAuthProperties;
import org.springyoung.auth.properties.YoungClientsProperties;
import org.springyoung.auth.service.YoungUserDetailService;
import org.springyoung.auth.translator.YoungWebResponseExceptionTranslator;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName 认证服务器相关的安全配置类
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 10:32
 * @Version 1.0
 */
//YoungAuthorizationServerConfigure继承AuthorizationServerConfigurerAdapter适配器
//@EnableAuthorizationServer注解标注，开启认证服务器相关配置
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class YoungAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    //注入了在YoungSecurityConfigure配置类中注册的BeanAuthenticationManager和PasswordEncoder
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    //private final RedisConnectionFactory redisConnectionFactory;
    private final YoungUserDetailService userDetailService;
    private final YoungAuthProperties authProperties;
    private final YoungWebResponseExceptionTranslator exceptionTranslator;
    private final TokenStore tokenStore;
    private final TokenEnhancer jwtTokenEnhancer;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    //private final DataSource dataSource;
    //MyBatis Plus配置了多数据源
    //private final DynamicRoutingDataSource dynamicRoutingDataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         *  客户端从认证服务器获取令牌的时候，必须使用client_id为young，client_secret为123456的标识来获取；
         *  该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌；
         *  在获取client_id为young的令牌的时候，scope只能指定为all，否则将获取失败；
         *  如果需要指定多个client，可以继续使用withClient配置
         */
        YoungClientsProperties[] clientsArray = authProperties.getClients();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(clientsArray)) {
            for (YoungClientsProperties client : clientsArray) {
                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(client.getScope())
                        .accessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
            }
        }
    }

    @Override
    @SuppressWarnings("all")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                //.tokenServices(defaultTokenServices())
                //让异常翻译器生效
                .exceptionTranslator(exceptionTranslator);

        //扩展token返回结果
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancerList);
            //jwt增强,指定该JwtAccessTokenConverter，用于后续JWT校验
            endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * tokenStore使用的是RedisTokenStore，认证服务器生成的令牌将被存储到Redis中
     */
    /*@Bean
    public TokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        // 解决每次生成的 token都一样的问题
        redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
        return redisTokenStore;
    }*/

    /**
     * 证服务器生成的令牌将被存储到数据库中
     */
    /*@Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }*/
    /**
     * 证服务器生成的令牌将被存储到数据库中(使用MyBatis Plus配置了多数据源，需要获取指定名称数据源)
     */
    /*@Bean
    public TokenStore tokenStore() {
        DataSource febsCloudBase = dynamicRoutingDataSource.getDataSource("febs_cloud_base");
        return new JdbcTokenStore(febsCloudBase);
    }*/

    /**
     * defaultTokenServices指定了令牌的基本配置，比如令牌有效时间为60 * 60 * 24秒，
     * 刷新令牌有效时间为60 * 60 * 24 * 7秒，
     * setSupportRefreshToken设置为true表示开启刷新令牌的支持
     */
    /*@Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        //开启刷新令牌的支持
        tokenServices.setSupportRefreshToken(true);
        //牌有效时间
        tokenServices.setAccessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds());
        //刷新令牌有效时间
        tokenServices.setRefreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
        return tokenServices;
    }*/

}