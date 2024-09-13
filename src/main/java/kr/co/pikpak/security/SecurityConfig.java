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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.pikpak.device.SHA256Encoder;
import kr.co.pikpak.device.UserType;
import kr.co.pikpak.service.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
		http.cors(cors-> cors.disable())
				.csrf(csrf -> csrf.disable())
				//.headers(header -> header.frameOptions(frame -> frame.disable()))
				.authorizeHttpRequests(
						(auth) -> auth
						/*
						.requestMatchers("/login", "/login/**").permitAll()
						.requestMatchers("/logout", "/logout/**").permitAll()
						.requestMatchers("/resources/**").permitAll()
						.requestMatchers("/favicon.ico").permitAll()
						*/
						.requestMatchers("/home").hasAnyAuthority(UserType.OPERATOR.toString(),UserType.SUPPLIER.toString(),UserType.VENDOR.toString())
						.requestMatchers("/post/**").hasAuthority(UserType.SUPPLIER.toString())
						.requestMatchers("/admin","/admin/**").hasAuthority("operator")
						.anyRequest().permitAll())
				/*
				.formLogin(
						(formLogin) -> formLogin.loginPage("/login")
						.usernameParameter("user_id")
						.passwordParameter("user_pw")
						.loginProcessingUrl("/login/auth")
						.defaultSuccessUrl("/home", true)
						.failureHandler(null))
				*/
				.authenticationProvider(authenticationProvider())
				//.addFilterBefore(JWTRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(JWTRequestFilter, BasicAuthenticationFilter.class)
				.logout((logoutConfig) -> logoutConfig.logoutSuccessUrl("/logout/end"));

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
	
	/*
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }
    */

}
