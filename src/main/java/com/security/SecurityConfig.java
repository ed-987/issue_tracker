package com.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//import com.auth0.example.AntPathRequestMatcher;

//@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final LogoutHandler logoutHandler;

	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }
	
	  @Override protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
          // allow all users to access the home pages and the static images directory
          .mvcMatchers("/", "/images/**","/h2-console/**","/CSS/**").permitAll()
          //.mvcMatchers("/user").hasRole("USERX")
          //.and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(getjwtAuthenticationConverter)
          // all other requests must be authenticated
          .anyRequest().authenticated()
          .and().oauth2Login()
          .and().logout()
          // handle logout requests at /logout path
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          // customize logout handler to log out of Auth0
          .addLogoutHandler(logoutHandler)
          //.and()
          //.httpBasic()
          //.realmName("oauth2/client")
          ;
		  
		  
		  //to access H2 console:
          http.csrf().disable();
	      http.headers().frameOptions().disable();
	  }
	 	
}	

