package com.surface1989.surface1989store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.surface1989.surface1989store.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/register", "/**/ws", "/plugins/**", "/dist/**", "/images/**", "/webjars/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.usernameParameter("email").defaultSuccessUrl("/admin").permitAll().and().logout()
				.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();

		/*
		 * http.authorizeRequests().antMatchers("/admin").authenticated().anyRequest().
		 * permitAll().and().formLogin()
		 * .loginPage("/login").usernameParameter("email").defaultSuccessUrl("/admin").
		 * permitAll().and().logout() .logoutSuccessUrl("/login").permitAll();
		 */

		/*
		 * http.authorizeRequests() .antMatchers("/**").authenticated()
		 * .anyRequest().permitAll() .and() .formLogin() .usernameParameter("email")
		 * .defaultSuccessUrl("/users") .permitAll() .and()
		 * .logout().logoutSuccessUrl("/login").permitAll();
		 */

		/*
		 * http.authorizeRequests() .antMatchers( "/register", "/plugins/**",
		 * "/dist/**", "/images/**", "/webjars/**" ).authenticated()
		 * .anyRequest().permitAll() .and() .formLogin() .usernameParameter("email")
		 * .defaultSuccessUrl("/users") .permitAll() .and()
		 * .logout().logoutSuccessUrl("/").permitAll();
		 */

	}
}