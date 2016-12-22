package com.oauth.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.oauth.integration.enums.RoleEnum;

/**
 * Configuration for the oauth2 resource server. Defines the application as stateless and configures the access to
 * resources via http.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    
    private static final String RESOURCE_ID = "restservice";

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /* deactivate sessions */
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.authorizeRequests()
        
            //Allow anonymous access
            .antMatchers("/authenticate/**").permitAll() //allow anonymous access
            .antMatchers("/register/**").permitAll() //allow anonymous access
            .antMatchers("/logout/**").permitAll() //allow anonymous access         
            .antMatchers("/country/**").permitAll() //allow anonymous access
            
            //allow only USER ROLE.
            .antMatchers("/user/**").hasAnyAuthority(RoleEnum.ROLE_USER.getLabel())
            
            //allow spring actuator for ADMIN ROLE.
            .antMatchers("/trace/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/health/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/loggers/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/metrics/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/autoconfig/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/info/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/env/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/heapdump/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/configprops/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/mappings/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/dump/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())            
            .antMatchers("/trace/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            .antMatchers("/beans/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.getLabel())
            
            //Any other request most be authenticated.
            .anyRequest().authenticated(); //other security settings    
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }
}