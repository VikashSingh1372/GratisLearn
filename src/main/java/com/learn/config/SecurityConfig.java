package com.learn.config;

import javax.servlet.FilterRegistration;
import org.springframework.web.filter.CorsFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.learn.security.CustomUserDetailService;
import com.learn.security.JwtAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.web.servlet.ServletContextInitializer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // to authorize role base requests used in admincontroller in delete												// user
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService csdt;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// any request means all request
		http.csrf().disable().authorizeHttpRequests().antMatchers("/auth/**","/admin/**","/quizzes/**","/user/**").permitAll()

		.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
		.antMatchers("/user/**").hasAnyAuthority("USER")

.antMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(this.authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(this.filter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.csdt).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public FilterRegistrationBean coresFilter() {

		UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();

		CorsConfiguration c = new CorsConfiguration();
		c.setAllowCredentials(true);
		c.addAllowedOriginPattern("*");
		c.addAllowedHeader("Authorization");
		c.addAllowedHeader("Content-Type");
		c.addAllowedHeader("Accept");
		c.addAllowedMethod("POST");
		c.addAllowedMethod("GET");
		c.addAllowedMethod("OPTIONS");
		c.addAllowedMethod("DELETE");
		c.addAllowedMethod("PUT");
		c.setMaxAge(3600L);
		
		s.registerCorsConfiguration("/**", c);
		

		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(s));
		bean.setOrder(-110);
		return bean;
	}

}
