package com.pouchen.roy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
/**
 * SpringBootWebSecurityConfiguration
 * WebMvcSecurityConfiguration
 */
@ComponentScan("com.pouchen.roy")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Bean
	public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) throws Exception {
		
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();  
		
		UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$10$NzK23OLdUVmogYTPDJm9X.B2PfPuXRA5WBJ/RP2ZxETWwM3lpJJvO").roles("ADMIN").build();
		UserDetails user = User.withUsername("roy").password("{bcrypt}$2a$10$NzK23OLdUVmogYTPDJm9X.B2PfPuXRA5WBJ/RP2ZxETWwM3lpJJvO").roles("USER").build();
		
		System.out.println(new BCryptPasswordEncoder().matches("password", admin.getPassword()));
		System.out.println(new BCryptPasswordEncoder().matches("password", user.getPassword()));
		
		manager.createUser(admin);
		manager.createUser(user);
		
		return manager;
		
	}
	
	protected void configure(HttpSecurity http) throws Exception {		
		http.authorizeRequests().  
	      antMatchers("/index","/").permitAll()  
	      .antMatchers("/admin","/user").authenticated()  
	      .and().formLogin().loginPage("/login")
	      .loginPage("/login?error")
//	      .failureUrl("/") redirect://
	      .and().logout()  
	      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	      .and().exceptionHandling().accessDeniedPage("/accessDenied");
		  
	}

}
