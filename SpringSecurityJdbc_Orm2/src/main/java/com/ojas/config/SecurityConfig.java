package com.ojas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	@Bean
	// For Customized we should use UserDetailsService
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}

	@Bean
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/rest/**").permitAll().antMatchers("/secure/rest/admin/add")
				.hasAnyAuthority("ADMIN").and().formLogin();
	}

	// 2)Authorization
//	@Bean
//	SecurityFilterChain configAuthorization(HttpSecurity http) throws Exception {
//
//		http.authorizeHttpRequests(re -> re.antMatchers("/rest/auth/process").permitAll()
//				.antMatchers("/secure/rest/admin/add").permitAll())
//				.formLogin(form -> form.loginPage("/login").permitAll()).logout(logout -> logout.permitAll());
//
//		return null;
//
//	}

	@Bean
	BCryptPasswordEncoder encodePWD() {

		return new BCryptPasswordEncoder();
	}

}
