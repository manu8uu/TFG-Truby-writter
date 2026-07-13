package com.tfg.truby_writer.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtGenerator jwtGenerator;

	@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.cors(Customizer.withDefaults())
        .csrf((csrf) -> csrf.disable())
        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(HttpMethod.POST, "/api/users/signUp").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
        	.requestMatchers(HttpMethod.POST, "/api/users/loginFromId").hasRole("USER")
            .requestMatchers(HttpMethod.POST, "/api/users/*/changePassword").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/users/search").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/users/username/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/users/*/block").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/users/*/unblock").hasRole("ADMIN")
            .anyRequest().hasRole("USER")); 

    return http.build();
}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Arrays.asList("*"));
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    
	    source.registerCorsConfiguration("/**", config);
	    
	    return source;
	    
	 }

}
