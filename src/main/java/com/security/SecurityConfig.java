package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth0.example.AntPathRequestMatcher;

//@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final LogoutHandler logoutHandler;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    
	/*
	 * @Autowired private BCryptPasswordEncoder passwordEncoder;
	 * 
	 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * @Autowired public void globalUserDetails(final AuthenticationManagerBuilder
	 * auth) throws Exception { // @formatter:off auth.inMemoryAuthentication()
	 * .withUser("e10002001-0@yahoo.com").password(passwordEncoder.encode("Test1234"
	 * )).roles("USER")
	 * //.and().withUser("tom").password(passwordEncoder.encode("111")).roles(
	 * "ADMIN").and()
	 * //.withUser("user1").password(passwordEncoder.encode("pass")).roles("USER").
	 * and()
	 * //.withUser("admin").password(passwordEncoder.encode("nimda")).roles("ADMIN")
	 * ; }// @formatter:on
	 */
	
	  @Override protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
          // allow all users to access the home pages and the static images directory
          .mvcMatchers("/", "/images/**","/homepage").permitAll()
          // all other requests must be authenticated
          .anyRequest().authenticated()
          .and().oauth2Login()
          .and().logout()
          // handle logout requests at /logout path
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          // customize logout handler to log out of Auth0
          .addLogoutHandler(logoutHandler)
          .and()
          .httpBasic()
          .realmName("oauth2/client")
          ;
	  }
	 	
}	

