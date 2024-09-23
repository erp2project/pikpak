package kr.co.pikpak.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.pikpak.device.SHA256Encoder;
import kr.co.pikpak.service.LoginService;

@Configuration
@EnableWebSecurity
public class JWTSecurityConfig {
	@Autowired
	private JWTUtility JWTUtil;
	
	@Autowired
	private LoginService LoginService;
	
	@Autowired 
	private JWTRequestFilter JWTRequestFilter;
	
	@Autowired
	private SHA256Encoder Encoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors-> cors.disable())
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(
					(auth) -> auth
					/*
					.requestMatchers("/login", "/login/**").permitAll()
					.requestMatchers("/logout", "/logout/**").permitAll()
					.requestMatchers("/resources/**").permitAll()
					.requestMatchers("/favicon.ico").permitAll()
					*/
					//.requestMatchers("/home").authenticated()
					//.requestMatchers("/supplier/**").hasAuthority("supplier")
					//.requestMatchers("/vendor/**").hasAuthority("vendor")
					.requestMatchers("/admin/users","/admin/users/**","/admin/user/**","/admin/check/**").hasAuthority("admin")
					//.requestMatchers("/admin","/admin/**").hasAuthority("admin")
					//.requestMatchers("/test").has
					.anyRequest().permitAll())
			.exceptionHandling(auth -> auth.accessDeniedHandler(accessDeniedHandler()))
			.formLogin(auth -> auth.disable())
			/*
			.formLogin(auth -> auth
					.loginPage("/login")
					.permitAll()
					)
			*/
			.httpBasic(auth -> auth.disable())
			.authenticationProvider(authenticationProvider())
			//.addFilterBefore(JWTRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(JWTRequestFilter, BasicAuthenticationFilter.class)
			.logout(config -> config.deleteCookies("accessToken").invalidateHttpSession(true).logoutSuccessUrl("/logout/end"));

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(LoginService);
		authenticationProvider.setPasswordEncoder(Encoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
}
