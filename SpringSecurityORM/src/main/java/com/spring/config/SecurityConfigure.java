package com.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	@Autowired
	private DataSource dataSource;// DriverManagerDataSource -->
	// 1. Authentication

	@Autowired
	public void authManager(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Welcome");
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username,password,enabled from users where username=?")// admin
				.authoritiesByUsernameQuery("select username,authority from authorities where username=?");

	}

	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		System.out.println("Got it bro");
//		http.authorizeHttpRequests( (req) -> req
//				 .antMatchers("/admin").hasRole(ADMIN)
//				 .antMatchers("/user").hasAnyRole(ADMIN,USER) .antMatchers("/").permitAll()
//				 .anyRequest().authenticated() ).formLogin();
//				 
		/*
		 * http.authorizeHttpRequests( (req) -> req
		 * .antMatchers("/admin").hasRole(ADMIN)
		 * .antMatchers("/user").hasAnyRole(ADMIN,USER) .antMatchers("/").permitAll()
		 * .anyRequest().authenticated() ).formLogin();
		 * 
		 * 
		 */
		// 2. Authorization

		http.authorizeHttpRequests().antMatchers("/home").permitAll().antMatchers("/admin").authenticated()
				.antMatchers("/user").hasAuthority("ROLE_ADMIN").antMatchers("/common")
				.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER").anyRequest().authenticated().and().formLogin()
				.defaultSuccessUrl("/welcome", true).and().logout().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and().exceptionHandling()
				.accessDeniedPage("/accessDenied").and().httpBasic();

		return http.build();
	}

}
