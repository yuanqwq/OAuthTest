package com.test.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurationAdapter extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authorizationManager;

    @Resource
    private UserDetailsService service;


    public final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(encoder.encode("123456"))
                .autoApprove(false)
                .scopes("all")
                .authorizedGrantTypes("client_credentials", "password", "implicit", "authorization_code", "refresh_token")
                .redirectUris("http://localhost:8082/login")
                .and()
                .withClient("test")
                .secret(encoder.encode("654321"))
                .autoApprove(false)
                .scopes("test1","test2")
                .authorizedGrantTypes("client_credentials", "password", "implicit", "authorization_code", "refresh_token")
                .redirectUris("http://localhost:8082/login");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authorizationManager)
                .userDetailsService(service);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(encoder)
                .allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }
}
