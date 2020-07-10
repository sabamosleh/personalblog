package com.blog.personalblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;


@Configuration
public class SpringConfig extends WebSecurityConfigurerAdapter {

    //it's a default bean which has been created by spring and carries our data source
    //properties which has been defined in application.properties
    //we need to pass it to AuthenticationManagerBuilder auth
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email,password,enabled from users where email=?")
                .authoritiesByUsernameQuery("select email,roles from authorities where email=?");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/users/rest/search","/users/rest/postsByUser/**","/posts/rest/search","/","/css/**","/img/**","/js/**")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers("/users/**","/categories/**")
                .hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").usernameParameter("email").permitAll().and().logout().permitAll();


    }


}
