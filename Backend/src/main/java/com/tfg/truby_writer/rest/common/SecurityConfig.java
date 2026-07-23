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
			.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()))
			.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/error").permitAll()

				// BASIC USER
				.requestMatchers(HttpMethod.POST, "/api/users/signUp").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users/loginFromId").hasRole("USER")
				.requestMatchers(HttpMethod.POST, "/api/users/*/changePassword").authenticated()

				// ADMIN
				.requestMatchers(HttpMethod.GET, "/api/users/search").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/users/username/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/users/*/block").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/users/*/unblock").hasRole("ADMIN")

				//PROJECT
            	.requestMatchers(HttpMethod.POST, "/api/structure/projects/createProject").hasRole("USER")
            	.requestMatchers(HttpMethod.GET, "/api/structure/projects/{id}").hasRole("USER")
            	.requestMatchers(HttpMethod.DELETE, "/api/structure/projects/deleteProject/{id}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/projects/getAllProjectsByUserId/{userId}").hasRole("USER")

				//PLOT
	            .requestMatchers(HttpMethod.POST, "/api/structure/projects/{projectId}/createPlot").hasRole("USER")
            	.requestMatchers(HttpMethod.PUT, "/api/structure/plots/modify/{plotId}").hasRole("USER")
            	.requestMatchers(HttpMethod.DELETE, "/api/structure/plots/delete/{plotId}").hasRole("USER")
            	.requestMatchers(HttpMethod.GET, "/api/structure/plots/{plotId}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/projects/getAllPlotsByProjectId/{projectId}").hasRole("USER")


				//PREMISE
            	.requestMatchers(HttpMethod.POST, "/api/structure/plots/{plotId}/createPremise").hasRole("USER")
            	.requestMatchers(HttpMethod.PUT, "/api/structure/premises/modify/{premiseId}").hasRole("USER")
            	.requestMatchers(HttpMethod.DELETE, "/api/structure/premises/delete/{premiseId}").hasRole("USER")
            	.requestMatchers(HttpMethod.GET, "/api/structure/premises/{premiseId}").hasRole("USER")
            	.requestMatchers(HttpMethod.GET, "/api/structure/plots/{plotId}/premises/search").hasRole("USER")

				//CHARACTERS
				.requestMatchers(HttpMethod.POST, "/api/structure/projects/{projectId}/createCharacter").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/api/structure/characters/modify/{characterId}").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/api/structure/characters/delete/{characterId}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/characters/{characterId}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/projects/{projectId}/characters/search").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/projects/{projectId}/characters/byName").hasRole("USER")

				//EVENTS & LINE TIMES
				.requestMatchers(HttpMethod.POST, "/api/structure/plots/{timelineId}/createEvent").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/api/structure/events/modify/{eventId}").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/api/structure/events/delete/{eventId}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/plots/{timelineId}/events/search").hasRole("USER")
				//.requestMatchers(HttpMethod.GET, "/api/structure/plots/{plotId}/lineTime").hasRole("USER")

				//NETWORK NODES & RELATIONSHIPS
            	.requestMatchers(HttpMethod.POST, "/api/structure/plots/{plotId}/network/characters").hasRole("USER")
            	.requestMatchers(HttpMethod.PUT, "/api/structure/plots/{plotId}/network/characters/{characterId}").hasRole("USER")
            	.requestMatchers(HttpMethod.DELETE, "/api/structure/plots/{plotId}/network/characters/{characterId}").hasRole("USER")
            	.requestMatchers(HttpMethod.POST, "/api/structure/plots/{plotId}/network/relationships").hasRole("USER")
            	.requestMatchers(HttpMethod.PUT, "/api/structure/plots/{plotId}/network/relationships").hasRole("USER")
            	.requestMatchers(HttpMethod.DELETE, "/api/structure/plots/{plotId}/network/relationships").hasRole("USER")


				//LOCATIONS
				.requestMatchers(HttpMethod.POST, "/api/structure/plots/*/createLocation").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/api/structure/modify/*").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/api/structure/delete/*").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/*").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/plots/*/search").hasRole("USER")

				// LOCATION POINTS
				.requestMatchers(HttpMethod.POST, "/api/structure/*/createPoint").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/api/structure/points/modify/*").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/api/structure/points/delete/*").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/structure/points/*").hasRole("USER")
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