package ge.edu.cu.l_lomidze2.sabakalavro.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ge.edu.cu.l_lomidze2.sabakalavro.filter.AuthenticationFilter;
import ge.edu.cu.l_lomidze2.sabakalavro.filter.BruteForceFilter;
import ge.edu.cu.l_lomidze2.sabakalavro.repository.UserRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain baseSecurity(
        HttpSecurity http,
        AuthenticationFilter authFilter,
        BruteForceFilter bruteForceFilter
    ) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(withDefaults())
            .authorizeHttpRequests(
                requestRegistry -> requestRegistry
                    .requestMatchers(
                        "/error",
                        "/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/h2-console/**"
                    ).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(
                configurer -> configurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;

            @Override
            public UserDetails loadUserByUsername(
                String username
            ) throws UsernameNotFoundException {
                var user = userRepository
                    .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

                return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                .build();
            }
        };
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
