package com.example.mini_project_task_manager.config;

<<<<<<< HEAD

=======
>>>>>>> develop
import com.example.mini_project_task_manager.filter.JwtAuthenticationFilter;
import com.example.mini_project_task_manager.handler.JsonAccessDeniedHandler;
import com.example.mini_project_task_manager.handler.JsonAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JsonAuthenticationEntryPoint authenticationEntryPoint;
    private final JsonAccessDeniedHandler accessDeniedHandler;

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Value("${cors.allowed-headers:*}")
    private String allowedHeaders;

    @Value("${cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${cors.exposed-headers:Authorization,Set-Cookie}")
    private String exposedHeaders;

    @Value("${security.h2-console:true}")
    private boolean h2ConsoleEnabled;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        List<String> origins = splitToList(allowedOrigins);

        config.setAllowCredentials(true);

        config.setAllowedHeaders(splitToList(allowedHeaders));
        config.setAllowedMethods(splitToList(allowedMethods));
        config.setExposedHeaders(splitToList(exposedHeaders));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        if (h2ConsoleEnabled) {
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
            //
        }

        http

                .authorizeHttpRequests(auth -> {

                    if (h2ConsoleEnabled)
                        auth.requestMatchers("/h2-console/**").permitAll();

                    auth

                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            .requestMatchers("/api/v1/auth/**").permitAll()

                            .requestMatchers("/api/v1/users/me/**").authenticated()

                            .requestMatchers(HttpMethod.GET, "/api/v1/boards/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/v1/boards/**").hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/boards/**").hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/boards/**").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/articles/**").permitAll()

                            .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/api/v1/stocks/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/stocks/**").hasAnyRole("ADMIN", "MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/stocks/**").hasAnyRole("ADMIN", "MANAGER")
                            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                            .anyRequest().authenticated();
                });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private static List<String> splitToList(String csv) {
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}