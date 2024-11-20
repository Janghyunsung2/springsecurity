package com.nhnacademy.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {




    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http
            .exceptionHandling(e -> e.accessDeniedPage("/403")); // 403 에러 발생 시 리다이렉트할 URL



        http.formLogin((formLogin) ->
                formLogin.loginPage("/auth/login")

                    .defaultSuccessUrl("/", true)
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .loginProcessingUrl("/auth/login/process"));



        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/private-project/**").hasRole("GOOGLE")
                .requestMatchers("/public-project/**").hasRole("MEMBER")
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/members").permitAll()
                .anyRequest().authenticated()
        );



        // login 로그인 폼을 사용한건데 기본설정으로 사용하겠다는 의미
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}
